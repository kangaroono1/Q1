export function convertUtcToChinaDate(utcDate: string): string {  
    // 创建一个Date对象，它会自动将UTC时间转换为中国时区时间
    const date = new Date(utcDate);  
    // 获取转换后的本地时间的年份、月份和日期部分  
    // 注意：月份是从0开始的，所以需要+1  
    const year = date.getFullYear();  
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 使用padStart确保月份总是两位数  
    const day = String(date.getDate()).padStart(2, '0'); // 使用padStart确保日期总是两位数
    // 拼接日期字符串  
    return `${year}-${month}-${day}`;   
}


function isDate(utcDate: Date) {  
    if (!(utcDate instanceof Date)) {  
        throw new Error('utcDate must be a Date object');  
    }  
}
