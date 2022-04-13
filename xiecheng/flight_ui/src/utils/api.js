import { get, post, remove } from './https.js'

// 股票接口
export const flight_api = {

    /** 
     * 测试接口
     * 名称：exam
     * 参数：paramObj/null
     * 方式：fetch/post/patch/put
     */
    exam(paramObj) {
        return get('/stock/15', paramObj)
    },

    /**
     * 查询批量航班线路
     * @param {*} paramObj 
     * @returns 
     */
    get_flight_list(paramObj) {
        return get('/flight/flights', paramObj)
    },

    /**
     * 查询航班线路
     * @param {*} paramObj 
     * @returns 
     */
     get_flight(paramObj) {
        return get('/flight/flight', paramObj)
    },

    /**
     * 删除航班
     * @param {*} paramObj 
     * @returns 
     */
    remove_flight(paramObj) {
        return remove('/flight/flight', paramObj)
    },

    /**
     * 查询乘客信息
     * @param {*} paramObj 
     * @returns 
     */
    get_passenger(paramObj) {
        return get('/order/passenger', paramObj)
    },

    /**
     * 添加乘客信息
     * @param {*} paramObj 
     * @returns 
     */
    post_passenger(paramObj) {
        return post('/order/passenger', paramObj)
    },

    /**
     * 删除乘客信息
     * @param {*} paramObj 
     * @returns 
     */
    remove_passenger(paramObj) {
        return remove('/order/passenger', paramObj)
    },

    /**
     * 提交订单
     * @param {*} paramObj 
     * @returns 
     */
    post_order(paramObj) {
        return post('/order/order', paramObj)
    },

    /**
     * 查询订单
     * @param {*} paramObj 
     * @returns 
     */
    get_order_list(paramObj) {
        return get('/order/orders', paramObj)
    },

    /**
     * 获取指定订单
     * @param {*} paramObj 
     * @returns 
     */
    get_order(paramObj) {
        return get('/order/order', paramObj)
    },

    /**
     * 删除订单
     * @param {*} paramObj 
     * @returns 
     */
    remove_order(paramObj) {
        return remove('/order/order', paramObj)
    },

    /**
     * 添加留言
     * @param {*} paramObj 
     * @returns 
     */
    post_message(paramObj) {
        return post('/message/message', paramObj)
    },

    /**
     * 获取备注
     * @param {*} paramObj 
     * @returns 
     */
    get_messsages(paramObj) {
        return get('/message/messages', paramObj)
    },

    /**
     * 删除备注
     * @param {*} paramObj 
     * @returns 
     */
    remove_messsage(paramObj) {
        return remove('/message/message', paramObj)
    },

    /**
     * 根据ip获取定位
     * @param {*} paramObj 
     * @returns 
     */
    get_location() {
        return get('/flight/location')
    }

}

// 支付宝接口
export const pay_api = {
    /**
     * 支付订单
     * @param {*} paramObj 
     * @returns 
     */
    pay(paramObj) {
        return post('/order/pay', paramObj)
    },
    
    /**
     * 查询支付情况
     * @param {*} paramObj 
     * @returns 
     */
    query_pay(paramObj) {
        return get('/order/query/pay', paramObj)
    }
}

// 用户接口
export const user_api = {
    /** 
     * 登录
     * 名称 login
     * 参数：paramObj/null
     * 方式：fetch/post/patch/put
     */
    login(paramObj) {
        return post('/auth/login', paramObj)
    },

    /**
     *
     * 获取用户信息
     * 名称 user_info
     * 参数：paramObj/null
     * 方式：fetch/post/patch/put
     *
     * @returns 
     */
    user_info() {
        return get('/auth/user')
    },
    /** 
     * 注册
     * 名称 register
     * 参数：paramObj/null
     * 方式：fetch/post/patch/put
     */
    register(paramObj) {
        return post('/auth/sign-in', paramObj)
    },
    /** 
     * 登出
     * 名称 logout
     * 参数：paramObj/null
     * 方式：fetch/post/patch/put
     */
    logout() {
        return get('/auth/logout')
    },

    /**
     * 用户列表
     * @param {*} paramObj 
     * @returns 
     */
     user_list(paramObj) {
        return get('/auth/users', paramObj)
    },
    
    /**
     * 删除用户
     * @param {*} paramObj 
     * @returns 
     */
    delete_user(paramObj) {
        return remove('/auth/user', paramObj)
    },
}








