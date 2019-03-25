package com.lnsoft.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created By Chr on 2019/3/14/0014.
 */
public interface CartMapper {

    List<Map<String,String>> queryLike(String name);

}
