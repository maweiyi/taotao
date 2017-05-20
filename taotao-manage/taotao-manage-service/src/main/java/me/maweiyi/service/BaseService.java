package me.maweiyi.service;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.maweiyi.pojo.BasePojo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Created by maweiyi on 5/17/17.
 */
public abstract class BaseService<T extends BasePojo> {


    @Autowired
    private Mapper<T> mapper;
    public Mapper<T> getMapper() {
        return this.mapper;
    };

    private Class<T> clazz;

    public BaseService() {
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType pType = (ParameterizedType) type;
        this.clazz = (Class<T>) pType.getActualTypeArguments()[0];
    }

    /**
     * 通过id查询数据
     *
     * @param id
     * @return
     */
    public T queryById(Long id) {

        return this.getMapper().selectByPrimaryKey(id);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public List<T> queryAll() {
        return this.getMapper().select(null);
    }

    public int queryAllCount() {
        return this.getMapper().selectCount(null);
    }

    public List<T> queryListByWhere(T t) {
        return this.getMapper().select(t);
    }

    public PageInfo<T> queryListByPage(T t, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<T> list = this.getMapper().select(t);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public T queryOne(T t) {
        //return this.getMapper().select(t).get(0);
        T result = this.getMapper().selectOne(t);
        return result;
    }

    public Integer save(T t) {
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return this.getMapper().insert(t);
    }

    public Integer updateById(T t) {
        t.setUpdated(new Date());
        return this.getMapper().updateByPrimaryKey(t);
    }

    public Integer updateByIdSelective(T t) {
        t.setUpdated(new Date());
        return this.getMapper().updateByPrimaryKeySelective(t);
    }

    public Integer deleteById(Long id) {
        return this.getMapper().deleteByPrimaryKey(id);
    }

    public Integer deleteByIds(List<Object> ids) {
        Example example = new Example(this.clazz);
        example.createCriteria().andIn("id", ids);
        return this.getMapper().deleteByExample(example);
    }

    public PageInfo<T> queryByPage(T t, Integer page, Integer rows, String order) throws IllegalAccessException {
        PageHelper.startPage(page, rows);
        Example example = new Example(this.clazz);

        if (StringUtils.isNotBlank(order)) {
            example.setOrderByClause(order);
        }

        if (t == null) {
            List<T> list = this.mapper.selectByExample(example);
            PageInfo<T> pageInfo = new PageInfo<T>(list);
            return pageInfo;
        }

        Example.Criteria criteria = example.createCriteria();
        Field[] fields = t.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(t) != null) {
                criteria.andEqualTo(field.getName(), field.get(t));

            }
        }

        List<T> list = this.mapper.selectByExample(example);
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        return pageInfo;
    }


}
