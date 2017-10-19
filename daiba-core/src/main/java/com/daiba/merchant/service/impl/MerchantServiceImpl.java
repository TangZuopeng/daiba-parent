package com.daiba.merchant.service.impl;

import com.daiba.merchant.dao.MerchantDao;
import com.daiba.merchant.model.Merchant;
import com.daiba.merchant.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.daiba.merchant.service.impl
 *
 * @author TinyDolphin
 *         2017/4/7 9:35.
 */
@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantDao merchantDao;

    @Override
    public List<Merchant> showAllMerchant(String schoolCode, String campusCode, String addTypeCode) {
        String code = schoolCode+campusCode+addTypeCode+"%";
        int state = 1;
        return merchantDao.selectAllMerchantByCode(code,state);
    }
}
