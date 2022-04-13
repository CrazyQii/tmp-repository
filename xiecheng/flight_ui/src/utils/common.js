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