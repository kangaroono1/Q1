export function isValidChinesePhoneNumber(rule: any, value: any, callback: any) {  
    if (value === '') {  
        callback(new Error('请输入手机号码'));  
    } else if (!/^1[3456789]\d{9}$/.test(value)) {  
        callback(new Error('请输入正确的手机号码'));  
    } else {  
        callback();  
    }  
}  