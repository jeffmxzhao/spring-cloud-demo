package com.kkl.demo.config;

/**
 * Created by Jeff on 2017/5/2.
 */
public class RedisConstant {
    /********************************************************************************************
     *  缓存数据库定义
     *********************************************************************************************/
    public enum RedisDBType{
        //系统缓存，shiro等(0)
        REDIS_CONSTANTS_DB,
        //存放SYS数据(1)
        REDIS_SYS_DB,
        //存放MD数据(2)
        REDIS_MD_DB,
        //存放SD数据(3)
        REDIS_SD_DB,
        //存放FI数据(4)
        REDIS_FI_DB,
        //存放全局序列号(5)
        REDIS_SEQ_DB,
        //存放锁(6)
        REDIS_LOCK_DB,
        //临时数据存放(7)
        // 上传附件过程中存放的临时记录，成功后更新至数据库，验证码存放
        // 订单下单统计
        REDIS_TEMP_DB,
        //记录队列处理计数(8)
        REDIS_MQ_DB,
        //APP登录数据(9)
        REDIS_APP_DB,
        //新APP登录数据库(10)
        REDIS_NEW_APP_DB,
        //区域，用于下单地址自动匹配(11)
        REDIS_SYS_AREA,
        //websocket & notice(12)
        REDIS_MS_DB,
        REDIS_DB13,
        REDIS_DB14,
        REDIS_TEST_DB
    }

    /********************************************************************************************
     *  SHIRO权限验证
     *********************************************************************************************/
    //客服区域
    public static final String SHIRO_KEFU_AREA = "shiro:area:%s";
    //客服/业务员负责的客户
    public static final String SHIRO_KEFU_CUSTOMER = "shiro:kefu:customers:%s";
    //用户菜单
    public static final String SHIRO_USER_MENU = "shiro:menu:%s";
    //机构
    public static final String USER_CACHE_LIST_BY_OFFICE_ID = "shiro:office:%s";
    //用户角色
    public static final String SHIRO_USER_ROLE = "shiro:role:%s";
    //用户Session
    public static final String SHIRO_USER_SESSION = "shiro:user:session:%s";
    //WebSocket Session(user_id,String类型)
    public static final String WEBSOCKET_SESSION = "WS:SESSION";

    /********************************************************************************************
     *  SYS缓存Key定义
     *********************************************************************************************/
    //数据字典
    public static final String SYS_DICT_TYPE = "DICT:%s";
    public static final String SYS_USER_ID = "user:id:%s";
    public static final String SYS_USER_LOGINNAME = "user:name:%s";
    //区域，按区域类型分别存储在set中，根据需要做并集等处理
    public static final String SYS_AREA_TYPE = "area:type:%s";
    //菜单
    public static final String SYS_MENU_ALL_LIST = "all:menu";
    //角色
    public static final String SYS_ROLE_ALL_LIST = "all:role";
    //机构
    public static final String SYS_OFFICE_ALL_LIST = "all:office";

    //部门-帐号
    public static final String SYS_OFFICE_USER = "SYS:OFFICE:USER:%s";//office_id

    /*客服部-帐号
    public static final String SYS_KEFU_USER = "SYS:KEFU:USER:%s";//office_id
    //业务部-帐号
    public static final String SYS_SALES_USER = "SYS:SALES:USER:%s";//office_id
    */
    // 客户-帐号
    public static final String SYS_CUSTOMER_USER = "SYS:CUSTOMER:USER:%s";//customer_id


    /********************************************************************************************
     *  MD缓存Key定义
     *********************************************************************************************/
    //产品分类信息
    public static final String MD_PRODUCT_CATEGORY = "MD:PRODUCT:CATEGORY:LIST";
    //产品信息
    public static final String MD_PRODUCT = "MD:PRODUCT:INFO";
    //产品服务信息
    public static final String MD_PRODUCTSERVICETYPE = "MD:PRODUCT:SERVICETYPE:INFO";

    //产品服务信息
    public static final String MD_PRODUCTSERVICETYPE_IDS = "MD:PRODUCT:SERVICETYPE:IDS";

    //所有产品列表
    public static final String MD_PRODUCT_ALL = "MD:PRODUCT:ALL";
    //套组产品列表
    public static final String MD_PRODUCT_SET = "MD:PRODUCT:SET";
    //非套组产品列表
    public static final String MD_PRODUCT_SINGLE = "MD:PRODUCT:SINGLE";
    //产品下的配件列表
    public static final String MD_PRODUCT_MATERIAL = "MD:PRODUCT:MATERIAL:%s";
    //客户关联产品列表
    public static final String MD_PRODUCT_CUSTOMER = "MD:PRODUCT:CUSTOMER:%s";
    //服务网点关联产品列表
    public static final String MD_PRODUCT_SERVICE_POINT = "MD:PRODUCT:SERVICEPOINT:%s";
    //配件信息
    public static final String MD_MATERIAL = "MD:MATERIAL:INFO";
    //所有配件列表
    public static final String MD_MATERIAL_ALL = "MD:MATERIAL:ALL";
    //产品关联配件列表
    public static final String MD_MATERIAL_PRODUCT = "MD:MATERIAL:PRODUCT:%s";
    // 服务类型
    public static final String MD_SERVICE_TYPE ="MD:SERVICETYPE:ALL";
    // 客评项目for短信
    public static final String MD_GRADE ="MD:GRADE:LIST";
    //客评项目for订单
    public static final String MD_ORDER_GRADE ="MD:GRADE:LIST:ORDER";
	//客户
    public static final String MD_CUSTOMER_ALL = "MD:CUSTOMER:ALL";
    //客户服务价格
    public static final String MD_CUSTOMER_PRICE = "MD:CUSTOMER:PRICE:%s";
    //网点
//    public static final String MD_SERVICEPOINT = "MD:SERVICEPOINT:%s";
    //网点
    public static final String MD_SERVICEPOINT_ALL = "MD:SERVICEPOINT:ALL";
    //网点价格
    public static final String MD_SERVICEPOINT_PRICE = "MD:SERVICEPOINT:PRICE:%s";
    //网点-安维
    public static final String MD_SERVICEPOINT_ENGINEER = "MD:SERVICEPOINT:ENGINEER:%s";

    //SD
    //订单
    public static final String SD_ORDER = "ORDER:%s";
    //导入订单
    public static final String SD_TMP_ORDER = "TMP:ORDER:%s";//userid
    //订单锁
    public static final String SD_ORDER_LOCK = "order:lock:%s";
    //接单锁
    public static final String SD_ORDER_ACCEPT_LOCK = "engineer:accept:%s";
    //派单锁
    public static final String SD_ORDER_PLAN_LOCK = "order:plan:%s";
    //配件附件
    public static final String SD_MATERIAL_ATTACHE = "material:attachment:%s";
    //返件附件
    public static final String SD_RETURN_MATERIAL_ATTACHE = "material:return:attachment:%s";
    //导入订单转单锁
    public static final String SD_TMP_ORDER_TRANSFER = "TMP:ORDER:TRANSFER:%s";
    //订单下单统计
    public static final String SD_CREATE_ORDER_LOG = "order:create:log:%s";

    /********************************************************************************************
     *  SEQ缓存Key定义
     *********************************************************************************************/
    // 滚号规则 -- SEQ:OrderNo
    public static final String SEQ_RULE = "SEQ:RULE:%s";
    // 滚号 -- SEQ:ORDER:20170707
    public static final String SEQ_KEY = "SEQ:%s:%s";

    /********************************************************************************************
     *  LOCK缓存Key定义
     *********************************************************************************************/
    // 滚号锁
    public static final String LOCK_SEQ_KEY = "LOCK:SEQ:%s:%s";
    // 对帐锁
    public static final String LOCK_CHARGE_KEY = "LOCK:CHARGE:%s";//orderId

    /********************************************************************************************
     *  队列缓存Key定义
     *********************************************************************************************/
    // 累计队列发送
    public static final String MQ_SS = "MQ:SS:%s";
    // 累计队列消费成功
    public static final String MQ_RS = "MQ:RS:%s";
    // 累计队列消费失败
    public static final String MQ_RE = "MQ:RE:%s";

    /********************************************************************************************
     *  提醒缓存Key定义  MS(Message)
     *********************************************************************************************/

    //待读问题反馈
    //按客户统计(哈希,key:customer_id,field:帐号id)
    public static final String MS_FEEDBACK_CUSTOMER = "MS:FEEDBACK:CUSTOMER:%s";
    //按区域统计（哈希,field:area_id)
    public static final String MS_FEEDBACK_KEFUBYAREA = "MS:FEEDBACK:KEFU:AREA";
    //客服按负责的客户统计（哈希,field:customer_id)
    public static final String MS_FEEDBACK_KEFUBYCUSTOMER = "MS:FEEDBACK:KEFU:BYCUSTOMER";

    //待处理问题反馈
    //按客户统计(哈希,key:customer_id,field:帐号id)
    public static final String MS_FEEDBACK_PENDING_CUSTOMER = "MS:FEEDBACK:PENDING:CUSTOMER:%s";
    //按区域统计（哈希,field:area_id)
    public static final String MS_FEEDBACK_PENDING_KEFUBYAREA = "MS:FEEDBACK:PENDING:KEFU:AREA";
    //客服按负责的客户统计（哈希,field:customer_id)
    public static final String MS_FEEDBACK_PENDING_KEFUBYCUSTOMER = "MS:FEEDBACK:PENDING:KEFU:BYCUSTOMER";

    //APP异常
    //按区域统计(哈希,field:area_id)
    public static final String MS_APP_ABNORMALY_KEFUBYAREA = "MS:APP:ABNORMALY:KEFU:AREA";
    //客服按负责的客户统计（哈希,field:customer_id)
    public static final String MS_APP_ABNORMALY_KEFUBYCUSTOMER = "MS:APP:ABNORMALY:KEFU:BYCUSTOMER";
    //FOR 客户
    //public static final String MS_APP_ABNORMALY_CUSTOMER = "MS:APP:ABNORMALY:CUSTOMER";
    /********************************************************************************************
     *  APP缓存Key定义
     *********************************************************************************************/
    public static final String APP_SESSION = "APP:LOGIN:%s";//user id

    /********************************************************************************************
     *  验证码缓存Key定义，存放于REDIS_TEMP_DB，7号数据库
     *********************************************************************************************/
    // 验证码，存活5分钟，VER:注册/重置密码，手机号码
    public static final String VERCODE_KEY = "VER:%d:%s";

}
