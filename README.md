# MyOkHttp
对okhttp网络框架的进一步封装，包含一个库文件和一个实例

## 主要功能
> 1.获取数据(后台返回的为json数据，需要解析成不同的数据类型<如String、JavaBean、JSON>时，使用不同的ResponseHandler即可；如果后台返回xml数据，需要参照response包下的类进行扩展)
> 2.提交数据(表单类型数据、String类型数据、json类型数据) 
> 3.带进度的上传和下载文件 
> 4.将请求保存(保存大小可设置)，支持以不同的形式取消单个、多个或全部请求 代码使用链式调用方式。
***
## 说明
> 运行之后的项目演示在文件上传和下载部分是需要本地服务器一起使用才能有结果，否则没有结果。
> 可以在<a href="http://download.csdn.net/download/itrenj/9797816"> CSDN中 </a>下载服务器的代码(压缩包同时也包含了以上MyOkHttp的代码)
> 在<a href="http://blog.csdn.net/itrenj/article/details/69787931"> CSDN博客中 </a>有okhttp简单使用的说明
