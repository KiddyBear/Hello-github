package com.bear.springcloud.controller;

import com.bear.springcloud.entities.CommonResult;
import com.bear.springcloud.entities.Payment;
import com.bear.springcloud.service.PaymentService;
import com.oracle.tools.packager.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @PostMapping("/payment/create")
//   ************ @RequestBody很重要，是对实体类的封装 ************
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        Log.info("*****插入结果： " + result);

        if (result > 0){
            return new CommonResult<>(200, "插入成功 + server port: " + port, result);
        }else{
            return new CommonResult(404, "插入失败+ server port: " + port , null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment paymentById = paymentService.getPaymentById(id);
        Log.info("*****插入结果： " + paymentById);
        if (paymentById != null) {
            return new CommonResult(200, "查询成功+ server port: " + port, paymentById);
        }else{
            return new CommonResult(444, "查询失败+ server port: " + port, null);
        }
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        return port;//返回服务接口
    }
}
