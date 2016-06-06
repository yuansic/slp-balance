package com.ai.slp.balance.api.accountquery.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.runner.base.exception.BusinessException;
import com.ai.slp.balance.api.accountquery.interfaces.IAccountQuerySV;
import com.ai.slp.balance.api.accountquery.param.AccountIdParam;
import com.ai.slp.balance.api.accountquery.param.AccountInfoVo;
import com.ai.slp.balance.api.accountquery.param.CustIdParam;
import com.ai.slp.balance.constants.ExceptCodeConstants;
import com.ai.slp.balance.service.business.interfaces.IAccountManagerSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service
@Component
public class AccountQuerySVImpl implements IAccountQuerySV {

    private static final Logger log = LogManager.getLogger(AccountQuerySVImpl.class);

    @Autowired
    private IAccountManagerSV accountSV;

    @Override
    public AccountInfoVo queryAccontById(AccountIdParam accountId) throws BusinessException,SystemException {
        log.debug("按账户ID查询账户开始");
        AccountInfoVo accountInfoVo = null;
        if (accountId == null) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "请求参数不能为空");
        }
        if (StringUtil.isBlank(accountId.getTenantId())) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
        }
        if (accountId.getAccountId() == 0) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "账户ID不能为空");
        }
        accountInfoVo = accountSV.queryAccountInfoById(accountId.getTenantId(),
                accountId.getAccountId());
        log.debug("账户查询结束");
        return accountInfoVo;
    }

    @Override
    public List<AccountInfoVo> queryAccontByCustId(CustIdParam custId) throws BusinessException,SystemException {

        log.debug("按客户UD查询账户开始");
        if (custId == null) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "请求参数不能为空");
        }
        if (StringUtil.isBlank(custId.getTenantId())) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
        }
        if (custId.getCustId() == 0) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "客户ID不能为空");
        }
        List<AccountInfoVo> accountInfoVoList = accountSV.queryAccountInfoByCustId(
                custId.getTenantId(), custId.getCustId());
        log.debug("账户查询结束");
        return accountInfoVoList;
    }

}
