package com.upg.cfsettle.prj.core;

import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjExt;
import com.upg.ucars.basesystem.component.core.IAttachmentDao;
import com.upg.ucars.basesystem.component.core.IAttachmentService;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.mapping.basesystem.other.Attachment;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by zuo on 2017/4/1.
 */
@Service
public class PrjExtServiceImpl implements IPrjExtService {

    @Autowired
    private IPrjExtDao prjExtDao;

    @Autowired
    private IAttachmentService attachmentService;

    @Override
    public void addPrjExt(CfsPrjExt prjExt) {
        prjExtDao.save(prjExt);
    }

    @Override
    public CfsPrjExt getPrjExtByPrjId(Long prjId) {
        CfsPrjExt prjExt = prjExtDao.get(prjId);
        if(prjExt != null){
            Attachment attachment;
            if(prjExt.getContractAttid() != null){
                attachment = attachmentService.getById(prjExt.getContractAttid());
                if(attachment != null){
                    prjExt.setContractName(attachment.getName());
                }
            }
            if(prjExt.getGuaranteAttid() != null){
                attachment = attachmentService.getById(prjExt.getGuaranteAttid());
                if(attachment != null){
                    prjExt.setGuaranteName(attachment.getName());
                }
            }
            if(prjExt.getPeriodAttid() != null){
                attachment = attachmentService.getById(prjExt.getPeriodAttid());
                if(attachment != null){
                    prjExt.setPeriodName(attachment.getName());
                }
            }
            if(prjExt.getSpreadAttid() != null){
                attachment = attachmentService.getById(prjExt.getSpreadAttid());
                if(attachment != null){
                    prjExt.setSpreadName(attachment.getName());
                }
            }
        }
        return prjExt;
    }


}
