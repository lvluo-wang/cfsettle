
package com.upg.ucars.framework.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.upg.ucars.constant.CommonConst;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.framework.exception.ValidateException;
import com.upg.ucars.tools.validator.ValidateResult;
import com.upg.ucars.tools.validator.ValidatorFactory;
import com.upg.ucars.tools.validator.annotation.EnumRange;
import com.upg.ucars.tools.validator.annotation.NotNull;



/**
 * 定义基本实体类，并预定义处理操作
 * 
 * @author alw
 * @author shentuwy
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseEntity implements  Cloneable,Serializable{
	
	/** 主键 */
	private Long id;
	public static final String ID = "id";
	
	/** 创建时间 */
	private Date createTime;
	public static final String CREATE_TIME = "createTime";
	/** 修改时间 */
	private Date modifyTime;
	public static final String MODIFY_TIME = "modifyTime";
	
	/** 创建人 */
	private Long creator;
	public static final String CREATOR = "creator";
	
	/** 修改人 */
	private Long modifier;
	public static final String MODIFIER = "modifier";
	
	/** 接入点 */
	private String miNo;
	public static final String MI_NO = "miNo";
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getModifier() {
		return modifier;
	}

	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}


	/** 删除标记,具体取值 为 0，1，-1  具体参考 ComDictItemConst中的定义**/
	@NotNull
	@EnumRange(enumValue = { CommonConst.DEL_FLAG_0, CommonConst.DEL_FLAG_1,CommonConst.STATUS_NEGATIVE_1 })
	private String delFlag = "0"; 
	
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	
    
    public String toString() {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o,excludeFields());
    }

    
    public int hashCode() {
    	
        return HashCodeBuilder.reflectionHashCode(this,excludeFields());
    }
    
    public Object clone() throws CloneNotSupportedException{ 

    	Object result = null;
		result = super.clone();

    	return result; 

    } 
	   
   
    /**
     * 过滤出集合类的属性，使其不参与到 在hashCode 和equals 的处理。
     * 主要解决 HashCodeBuilder类在Hibernate下存在集合类的属性时，生成hashCode的出现异常的问题。
     * 针对具体的类可以重载该方法，自己指定需要剔除的字段属性名（一般是将集合类的属性剔除掉)
     * @return
     */
    @SuppressWarnings("rawtypes")
	public List<String> excludeFields()
    {
    	List<String> excludeFields = new ArrayList<String>();
    	Field[] fields = this.getClass().getDeclaredFields();
    	 for (int i = 0; i < fields.length; ++i) {
             Field field = fields[i];
             Class[] cls = field.getType().getInterfaces();
             if(cls!=null && cls.length>0)
             for(int j=0;j<cls.length;j++)
             {
            	 String interfaceName = cls[j].getName();
            	 if("java.util.Collection".equals(interfaceName)||"java.lang.Iterable".equals(interfaceName))
                	 excludeFields.add(field.getName());
             }
    	 }
    	 
    	 return excludeFields;
    }
    
   
    
    
    
   /** 
    * 合法性较验
    *  进行多个属性之间关联关系及规则的验证
    *  @return boolean   true:验证通过；false验证不通过
    */
   public boolean validate()throws ValidateException{
		try {
			ValidateResult result =	ValidatorFactory.getInstance().validate(this);
			if(result == null){
				return true;
			}
			if(!result.isPass()){
				
				throw new Exception(result.getErrorMessage());
			}
		} catch (Exception e) {
			ExceptionManager
					.throwException(ValidateException.class, ErrorCodeConst.COMMON_ERROR_CODE, e.getMessage(),e);
		}
		return true;
	   
   }
   
   /** 
    * 转为垃圾删除数据
    *  在该方法中可以将对实体进行逻辑删除处理，比如自身状态及关联实体的状态的修改
    */
   public  void turnDump()throws ServiceException
   {
      //TODO: 执行逻辑删除前的一些状态处理
	   this.setDelFlag("-1");
   }
   
   /** 判断是否允许删除,默认允许删除
    *  主要进行删除前状态的验证，执行删除前，必须通过该方法的校验
    *  @return boolean   true:允许删除，false：不允许删除
    */
   public boolean canDelete()throws ValidateException{
	   return true;
   }



}