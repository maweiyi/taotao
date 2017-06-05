package me.maweiyi.sso.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by maweiyi on 5/18/17.
 */

@Service
public class PropertiesService {

    @Value("${IMAGE_FILE_PATH}")
    public String IMAGE_FILE_PATH;

    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;
}
