package com.thinkjoy.web.server.controller;

import com.thinkjoy.common.util.RedisUtil;
import com.thinkjoy.web.rpc.api.WeatherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

@Controller
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/manage/weather", produces = "text/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "天气查询")
    String getWeather() throws Exception {
        Jedis jedis = null;
        String weather;

        try {
            jedis = RedisUtil.getJedis();
            weather = jedis.get("weather");
            if (weather == null) throw new Exception();

        } catch (Exception e) {
            weather = weatherService.getWeather();
            jedis.set("weather", weather);
            jedis.expire("weather", 60 * 60);
        }

        return weather;
    }
}
