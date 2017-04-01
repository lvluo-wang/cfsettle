package com.upg.cfsettle.prj.core;

import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjExt;
import com.upg.cfsettle.util.RateUtil;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.util.BeanUtils;
import com.upg.ucars.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * Created by zuo on 2017/3/30.
 */
@Service
public class PrjServiceImpl implements IPrjService {


    @Autowired
    private IPrjDao prjDao;
    @Autowired
    private IPrjExtDao prjExtDao;

    @Autowired
    private IPrjExtService prjExtService;

    @Override
    public List<Map<String, Object>> findByCondition(CfsPrj searchBean, Page page) {
        return prjDao.findByCondition(searchBean,page);
    }

    @Override
    public void addPrjApply(CfsPrj prj) {
        prjDao.save(prj);
    }

    @Override
    public void savePrjAndPrjExt(CfsPrj prj, CfsPrjExt prjExt) {
        prj.setMtime(DateTimeUtil.getNowDateTime());
        prj.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
        prj.setStatus(Byte.valueOf("1"));
        prj.setRemainingAmount(prj.getDemandAmount());
        dealPrjTimeLimitDay(prj);
        //prj.setYearRate(RateUtil.rateToPercent(prj.getYearRate()));
        //prj.setPeriodRate(RateUtil.rateToPercent(prj.getPeriodRate()));
        prjDao.save(prj);
        prjExt.setPrjId(prj.getId());
        prjExt.setCtime(DateTimeUtil.getNowDateTime());
        prjExt.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
        prjExtService.addPrjExt(prjExt);
    }

    private void dealPrjTimeLimitDay(CfsPrj prj) {
        if(prj.getTimeLimitUnit() == "Y"){
            prj.setTimeLimitDay(prj.getTimeLimit() * 365);
        }
        if(prj.getTimeLimitUnit() == "M"){
            prj.setTimeLimitDay(prj.getTimeLimit() * 30);
        }
    }

    @Override
    public CfsPrj getPrjById(Long id) {
        CfsPrj prj =  prjDao.get(id);
        //prj.setYearRate(RateUtil.percentToRate(prj.getYearRate()));
        //prj.setPeriodRate(RateUtil.percentToRate(prj.getPeriodRate()));
        return prj;
    }

    @Override
    public void updatePrjAndPrjExt(CfsPrj prj, CfsPrjExt prjExt) {
        prj.setMtime(DateTimeUtil.getNowDateTime());
        prj.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
        prjDao.update(prj);
        prjExt.setCtime(DateTimeUtil.getNowDateTime());
        prjExt.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
        prjExtDao.update(prjExt);
    }

    @Override
    public void auditPrjAndPrjExt(CfsPrj prj, CfsPrjExt prjExt) {
        CfsPrj auditPrj = prjDao.get(prj.getId());
        try {
            BeanUtils.copyNoNullProperties(auditPrj,prj);
            prj.setStatus(Byte.valueOf("2"));
            auditPrj.setMtime(DateTimeUtil.getNowDateTime());
            auditPrj.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
            prjDao.update(auditPrj);
            prjExt.setCtime(DateTimeUtil.getNowDateTime());
            prjExt.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
            prjExtDao.update(prjExt);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
