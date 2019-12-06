package com.roncoo.eshop.datalink.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "eshop-product-service",fallbackFactory = ProductServiceFallbackFactory.class)
public interface EshopProductService {

    @RequestMapping(value = "/brand/findById",method = RequestMethod.GET)
    String findBrandById(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "/brand/findByIds",method = RequestMethod.GET)
    String findBrandByIds(@RequestParam(value = "ids") String ids);

    @RequestMapping(value = "/category/findById",method = RequestMethod.GET)
    String findCategoryById(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "/product-intro/findById",method = RequestMethod.GET)
    String findProductIntroById(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "/product-property/findById",method = RequestMethod.GET)
    String findProductPropertyById(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "/product-property/findByProductId",method = RequestMethod.GET)
    String findProductPropertyByProductId(@RequestParam(value = "productId") Long productId);

    @RequestMapping(value = "/product/findById",method = RequestMethod.GET)
    String findProductById(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "/product-specification/findById",method = RequestMethod.GET)
    String findProductSpecificationById(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "/product-specification/findByProductId",method = RequestMethod.GET)
    String findProductSpecificationByProductId(@RequestParam(value = "productId") Long productId);
}

@Component
class ProductServiceFallbackFactory implements feign.hystrix.FallbackFactory<EshopProductService>{
    @Override
    public EshopProductService create(Throwable cause) {
        return new EshopProductService() {
            @Override
            public String findBrandById(Long id) {
                return null;
            }

            @Override
            public String findBrandByIds(String ids) {
                return null;
            }

            @Override
            public String findCategoryById(Long id) {
                return null;
            }

            @Override
            public String findProductIntroById(Long id) {
                return null;
            }

            @Override
            public String findProductPropertyById(Long id) {
                return null;
            }

            @Override
            public String findProductPropertyByProductId(Long productId) {
                return null;
            }

            @Override
            public String findProductById(Long id) {
                return null;
            }

            @Override
            public String findProductSpecificationById(Long id) {
                return null;
            }

            @Override
            public String findProductSpecificationByProductId(Long productId) {
                return null;
            }
        };
    }
}
