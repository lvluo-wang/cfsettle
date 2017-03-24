package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.mapping.basesystem.dictionary.DictArea;



/**
 * 提供对DictArea 数据访问功能
 *
 * @author anlw
 */
public interface IDictAreaDAO extends IBaseDAO<DictArea, Long> {
	
	/**
    * 获取身份列表
    * @param pid
    * @return
    */
    public List<DictArea> getProvinceList();
   /**
    * 获取父级区域下的子区域
    * @param pid
    * @return
    */
    public List<DictArea> getAreaListByPid(Long pid);
    /**
     * 根据Code获取地区
     * @param pid
     * @return
     */
    public List<DictArea> getAreaListByCode(String code);
	
    
}
