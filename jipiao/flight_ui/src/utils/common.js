export function formatDate(timestamp) {
    // 比如需要这样的格式 yyyy-MM-dd hh:mm:ss
    let date = new Date(Number(timestamp));
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let hour = date.getHours();
    let min = date.getMinutes();
    let seconds = date.getSeconds();
    month = month < 10 ? ('0' + month) : month
    day = day < 10 ? ('0' + day) : day
    hour = hour < 10 ? '0' + hour : hour
    min = min < 10 ? '0' + min : min
    seconds = seconds < 10 ? '0' + seconds : seconds
    return `${year}-${month}-${day} ${hour}:${min}:${seconds}`

}


export function getTaskTime(strDate) {	
    if(null==strDate || ""==strDate){
        return "";
    }
    var dateStr=strDate.trim().split(" ");
    var strGMT = dateStr[0]+" "+dateStr[1]+" "+dateStr[2]+" "+dateStr[5]+" "+dateStr[3]+" "+ dateStr[4]+" GMT+0800";
    var date = new Date(Date.parse(strGMT));
    var y = date.getFullYear();
    var m = date.getMonth() + 1;  
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    var minute = date.getMinutes();  
    minute = minute < 10 ? ('0' + minute) : minute;
    var second = date.getSeconds();
    second = second < 10 ? ('0' + second) : second;
    
    return y+"-"+m+"-"+d+" "+h+":"+minute+":"+second;
}


export function getTaskDate(strDate) {	
    if(null==strDate || ""==strDate){
        return "";
    }
    var dateStr=strDate.trim().split(" ");
    var strGMT = dateStr[0]+" "+dateStr[1]+" "+dateStr[2]+" "+dateStr[5]+" "+dateStr[3]+" "+ dateStr[4]+" GMT+0800";
    var date = new Date(Date.parse(strGMT));
    var y = date.getFullYear();
    var m = date.getMonth() + 1;  
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;
    
    return y+"-"+m+"-"+d;
}


// 返回格式为xx天xx小时xx分钟
export function get_interval_time(faultDate, completeTime) {
    var stime = Date.parse(new Date(faultDate));
    var etime = Date.parse(new Date(completeTime));
    // 两个时间戳相差的毫秒数
    var usedTime = etime - stime;
    // 计算相差的天数  
    var days = Math.floor(usedTime / (24 * 3600 * 1000));
    // 计算天数后剩余的毫秒数
    var leave1 = usedTime % (24 * 3600 * 1000);  
    // 计算出小时数  
    var hours = Math.floor(leave1 / (3600 * 1000));
    // 计算小时数后剩余的毫秒数
    var leave2 = leave1 % (3600 * 1000);        
    // 计算相差分钟数
    var minutes = Math.floor(leave2 / (60 * 1000));
    var time = days + "天" + hours + "时" + minutes + "分";
    return time;
}

export const city_options = [
    {
        value: '宁夏回族自治区',
        label: '宁夏回族自治区',
        children: [
        {
            value: '银川市',
            label: '银川市'
        },
        ],
    },
    {
        value: '陕西省',
        label: '陕西省',
        children: [
        {
            value: '西安市',
            label: '西安市'
        },
        ],
    },
    {
        value: '甘肃省',
        label: '甘肃省',
        children: [
        {
            value: '兰州市',
            label: '兰州市'
        },
        ],
    },
    {
        value: '青海省',
        label: '青海省',
        children: [
        {
            value: '西宁市',
            label: '西宁市'
        },
        ],
    },   
    {
        value: '山西省',
        label: '山西省',
        children: [
        {
            value: '太原市',
            label: '太原市'
        },
        ],
    },    
    {
        value: '黑龙江省',
        label: '黑龙江省',
        children: [
        {
            value: '哈尔滨市',
            label: '哈尔滨市'
        },
        ],
    },
    {
        value: '辽宁省',
        label: '辽宁省',
        children: [
        {
            value: '沈阳市',
            label: '沈阳市'
        },
        ],
    }, 
    {
        value: '吉林省',
        label: '吉林省',
        children: [
        {
            value: '长春市',
            label: '长春市'
        },
        ],
    },
    {
        value: '安徽省',
        label: '安徽省',
        children: [
        {
            value: '合肥市',
            label: '合肥市'
        },
        ],
    },
    {
        value: '福建省',
        label: '福建省',
        children: [
        {
            value: '福州市',
            label: '福州市'
        },
        ],
    },
    {
        value: '江西省',
        label: '江西省',
        children: [
        {
            value: '南昌市',
            label: '南昌市'
        },
        ],
    }, 
    {
        value: '山东省',
        label: '山东省',
        children: [
        {
            value: '济南市',
            label: '济南市'
        },
        ],
    },  
    {
        value: '河北省',
        label: '河北省',
        children: [
        {
            value: '石家庄',
            label: '石家庄'
        },
        ],
    },
    {
        value: '河南省',
        label: '河南省',
        children: [
        {
            value: '郑州市',
            label: '郑州市'
        },
        ],
    },  
    {
        value: '湖北省',
        label: '湖北省',
        children: [
        {
            value: '武汉市',
            label: '武汉市'
        },
        ],
    },  
    {
        value: '湖南省',
        label: '湖南省',
        children: [
        {
            value: '长沙市',
            label: '长沙市'
        },
        ],
    }, 
    {
        value: '广东省',
        label: '广东省',
        children: [
        {
            value: '广州市',
            label: '广州市'
        },
        ],
    }, 
    {
        value: '海南省',
        label: '海南省',
        children: [
        {
            value: '海口市',
            label: '海口市'
        },
        ],
    }, 
    {
        value: '四川省',
        label: '四川省',
        children: [
        {
            value: '成都市',
            label: '成都市'
        },
        ],
    }, 
    {
        value: '贵州省',
        label: '贵州省',
        children: [
        {
            value: '贵阳市',
            label: '贵阳市'
        },
        ],
    }, 
    {
        value: '云南省',
        label: '云南省',
        children: [
        {
            value: '昆明市',
            label: '昆明市'
        },
        ],
    }, 
    {
        value: '台湾省',
        label: '台湾省',
        children: [
        {
            value: '台北市',
            label: '台北市'
        },
        ],
    },    
    {
        value: '浙江省',
        label: '浙江省',
        children: [
        {
            value: '杭州市',
            label: '杭州市'
        },
        ],
    },
    {
        value: '江苏省',
        label: '江苏省',
        children: [
        {
            value: '南京市',
            label: '南京市'
        },
        ],
    },
    {
        value: '北京市',
        label: '北京市'
    },
    {
        value: '上海市',
        label: '上海市'
    },
    {
        value: '天津市',
        label: '天津市'
    },
    {
        value: '重庆市',
        label: '重庆市'
    },
    {
        value: '香港特别行政区',
        label: '香港特别行政区'
    },
    {
        value: '澳门特别行政区',
        label: '澳门特别行政区'
    },
    {
        value: '广州省',
        label: '广州省',
        children: [
        {
            value: '深圳市',
            label: '深圳市'
        },
        ],
    },
    {
        value: '内蒙古自治区',
        label: '内蒙古自治区',
        children: [
        {
            value: '呼和浩特市',
            label: '呼和浩特市'
        },
        ],
    }, 
    {
        value: '广西壮族自治区',
        label: '广西壮族自治区',
        children: [
        {
            value: '南宁市',
            label: '南宁市'
        },
        ],
    }, 
    {
        value: '西藏自治区',
        label: '西藏自治区',
        children: [
        {
            value: '拉萨市',
            label: '拉萨市'
        },
        ],
    }, 
    {
        value: '新疆维吾尔自治区',
        label: '新疆维吾尔自治区',
        children: [
        {
            value: '乌鲁木齐市',
            label: '乌鲁木齐市'
        },
        ],
    },    
] 

