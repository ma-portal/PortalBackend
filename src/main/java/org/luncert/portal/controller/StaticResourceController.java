package org.luncert.portal.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import org.luncert.portal.service.StaticResourceService;
import org.luncert.portal.util.IOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/static-resource")
public class StaticResourceController {

    @Autowired
    private StaticResourceService service;

    @GetMapping("/{id}")
    public void getResource(@PathVariable("id") String resId,
        HttpServletResponse rep) throws IOException {
        byte[] data = service.get(resId);
        if (data != null) {
            IOHelper.writeResponse(null, data, rep, true);
        } else {
            JSONObject errmsg = new JSONObject();
            errmsg.put("errmsg", "could not find target resource");
            rep.setStatus(404);
            IOHelper.writeResponse("application/json",
                errmsg.toJSONString().getBytes(), rep, true);
        }
    }

}