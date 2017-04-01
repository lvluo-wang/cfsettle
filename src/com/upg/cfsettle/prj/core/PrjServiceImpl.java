package com.upg.cfsettle.prj.core;

import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjExt;
import com.upg.cfsettle.util.RateUtil;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;

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
        prj.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
        prj.setStatus(Byte.valueOf("1"));
        prj.setRemainingAmount(prj.getDemandAmount());
        //prj.setYearRate(RateUtil.rateToPercent(prj.getYearRate()));
        //prj.setPeriodRate(RateUtil.rateToPercent(prj.getPeriodRate()));
        prjDao.save(prj);
        prjExt.setPrjId(prj.getId());
        prjExt.setMtime(DateTimeUtil.getNowDateTime());
        prjExt.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
        prjExtService.addPrjExt(prjExt);
    }

    @Override
    public CfsPrj getPrjById(Long id) {
        CfsPrj prj =  prjDao.get(id);
        //prj.setYearRate(RateUtil.percentToRate(prj.getYearRate()));
        //prj.setPeriodRate(RateUtil.percentToRate(prj.getPeriodRate()));
        return prj;
    }
}
