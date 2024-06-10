const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;  
 
export function isValidEmail(rule: any, value: any, callback: any) {  
  if (value === '') {  
    // callback(new Error('请输入邮箱地址'));
    callback(); 
  } else if (!emailRegex.test(String(value).toLowerCase())) {  
    callback(new Error('请输入正确的邮箱地址'));  
  } else {  
    callback();  
  }  
}