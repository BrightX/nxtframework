package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.service.NxtProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductOrderSwapController {

    @Resource
    private NxtProductService nxtProductService;

    @RequestMapping(value = "/api/admin/product/order_swap", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "product_id_a", required=false) Long productIdA,
                                     @RequestParam(value = "product_id_b", required=false) Long productIdB
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (productIdA == null || productIdB == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先查询*/
        NxtProduct contentA = nxtProductService.queryById(productIdA);
        if (contentA == null){
            result.put("status", 49);
            result.put("message", "对应的A不存在");
            return result;
        }
        NxtProduct contentB = nxtProductService.queryById(productIdB);
        if (contentB == null){
            result.put("status", 49);
            result.put("message", "对应的B不存在");
            return result;
        }

        long sortA = contentA.getSortId();
        long sortB = contentB.getSortId();

        /*交换*/
        contentA.setSortId(sortB);
        contentB.setSortId(sortA);

        nxtProductService.update(contentA);
        nxtProductService.update(contentB);

        return result;

    }

}
