# MyOkHttp
对okhttp网络框架的进一步封装，包含一个库文件和一个实例

## 主要功能说明
> 1.获取数据(后台返回的为json数据，需要解析成不同的数据类型<如String、JavaBean、JSON>时，使用不同的ResponseHandler即可；如果后台返回xml数据，需要参照response包下的类进行扩展)<br/>
> 2.提交数据(表单类型数据、String类型数据、json类型数据) <br/>
> 3.带进度的上传和下载文件 <br/>
> 4.将请求保存(保存大小可设置)，支持以不同的形式取消单个、多个或全部请求 代码使用链式调用方式<br/>

## 使用方式
### Get 请求
> Get方式获取String类型的数据
	
	MyOkHttpUtil.getRequest()
                .url(Constant.ALL_URL)
                .enqueue(new StringResponseHandler() {
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult.setText(result);
                    }

                    @Override
                    public void onNetWork() {
                        // 没有网络连接时回调
                        Toast.makeText(GetRequestActivity.this, "无网络连接", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Call call,MyOkHttpExecption error) {
                        // 处理错误
                        Log.e("GetRequestActivity",error + "");
                    }
                });

> Get方式获取Bean类型的数据
	
	MyOkHttpUtil.getRequest()
                .url(Constant.BASE_URL)
                .param("cityCode", "101040100") // 不使用完整连接，添加参数
                .param("weatherType", "1")
                .enqueue(new BeanResponseHandler<WeatherBean>() {
                    @Override
                    public void onSucceed(Call call, WeatherBean result) {
                        WeatherBean.WeatherinfoEntity weatherinfo = result.getWeatherinfo();
                        beanResult.setText(weatherinfo.getCity() + weatherinfo.getWD());
                    }
                });

### Post 请求
> Post 请求获取 JsonObject 类型数据
	
	MyOkHttpUtil.postKeyValuRequest()
                .url(Constant.ALL_URL)
                .enqueue(new JsonObjectResponseHandler() {
                    @Override
                    public void onSucceed(Call call, JSONObject result) {
                        try {
                            objResult.setText(result.getJSONObject("weatherinfo").getString("city"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("PostGetDataActivity", e + "");
                        }
                    }
                });

> Post 请求提交键值对参数获取 Bean 类型数据
	
	MyOkHttpUtil.postKeyValuRequest().url(Constant.BASE_URL)
                .param("cityCode", "101040100") // 不使用完整连接，添加参数
                .param("weatherType", "1")
                .enqueue(new BeanResponseHandler<WeatherBean>() {
                    @Override
                    public void onSucceed(Call call, WeatherBean result) {
                        WeatherBean.WeatherinfoEntity weatherinfo = result.getWeatherinfo();
                        beanResult.setText(weatherinfo.getCity() + weatherinfo.getWD());
                    }
                });
> Post 请求提交表单数据

	MyOkHttpUtil.postFormRequest()
                .url(Constant.FILE_UP)
                .param("username", "abced")
                .param("psw", "123456")
                .param("nick", "hehe")
                .tag("aaa")
                .enqueue(new StringResponseHandler() {
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult.setText(result);
                    }
                });
> Post 请求提交 String 类型数据
	
	String string = "姓名：张三；年龄：22,；性别：男";
        MyOkHttpUtil.postStringRequest()
                .url(Constant.JSON_URL)
                .string(string)
                .enqueue(new StringResponseHandler() {
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult2.setText(result);
                    }
                });

> Post 请求提交 json 类型数据
	
	String jsonString = "{\n" +
                "    \"age\": 22,\n" +
                "    \"name\": \"zhangsan\",\n" +
                "    \"nick\": \"hehe\",\n" +
                "    \"sex\": \"man\"\n" +
                "}";
        MyOkHttpUtil.postJsonRequest()
                .url(Constant.JSON_URL)
                .json(jsonString)
                .enqueue(new StringResponseHandler() {
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult3.setText(result);
                    }
                });

### 上传文件[带进度条]
> 单个文件上传

	File directory = Environment.getExternalStorageDirectory();
        File file = new File(directory,"Pictures/upload1.jpg");
        MyOkHttpUtil.upLoadFileRequest()
                .url(Constant.FILE_UP)
                .file("filename",file)
                .enqueue(new StringResponseHandler(){
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult.setText(result);
                    }

                    @Override
                    public void onProgress(long completeLength, long totalLength, boolean isFinish) {
                        // 更新进度
                        singleBar.setMax((int) (totalLength / 100));
                        singleBar.setProgress((int)(completeLength / 100));

                        if(isFinish){
                            Toast.makeText(UpFileActivity.this, "单个文件上传完成", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

> 多个文件上传

	File directory = Environment.getExternalStorageDirectory();
        File file1 = new File(directory,"Pictures/upload1.jpg");
        File file2 = new File(directory,"Pictures/upload2.jpg");
        MyOkHttpUtil.upLoadFileRequest()
                .url(Constant.FILE_UP)
                .param("username", "abced")
                .param("psw", "123456")
                .file("filename", file1)
                .addFile("filename", file2)
                .enqueue(new StringResponseHandler() {
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult2.setText(result);
                    }

                    @Override
                    public void onProgress(long completeLength, long totalLength, boolean isFinish) {
                        MultipleBar.setMax((int) (totalLength / 100));
                        MultipleBar.setProgress((int) (completeLength / 100));

                        if (isFinish) {
                            Toast.makeText(UpFileActivity.this, "多个文件上传完成", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

### 下载文件[带进度条]

	File directory = Environment.getExternalStorageDirectory();
        File file = new File(directory,"Pictures/downfile.png");
        MyOkHttpUtil.downloadFileRequest()
                .url(Constant.FILE_DOWN)
                // .fileDir(new File(directory,"Pictures"))
                // .fileName("downfile.png")
                // 设置了filePath()，那么设置fileDir()和fileName()的值无效
                .filePath(file)
                .enqueue(new DownLoadResponseHandler() {
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult.setText("文件保存路径：" + result);
                    }

                    @Override
                    public void onProgress(long completeLength, long totalLength, boolean isFinish) {
                        // 更新进度
                        progressBar.setMax((int) (totalLength / 100));
                        progressBar.setProgress((int)(completeLength / 100));

                        if(isFinish){
                            Toast.makeText(DownLoadActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFilePathException(MyOkHttpExecption myOkHttpExecption) {
                        // 指定的文件保存路径有问题
                        Log.e("DownLoadActivity", myOkHttpExecption + "");
                    }
                });

### 取消请求
> 取消单个请求

	MyOkHttpUtil.cancel("aaa");
> 取消多个请求
	
	MyOkHttpRequestManager.cancelAll();

## 代码说明和运行注意事项
> 1.代码中的 MyOkHttpServer 是服务器代码，需要部署到Tomcat服务器上  
> 2.运行的项目演示在文件上传和下载部分是需要本地服务器一起使用才能有结果，否则没有结果(所以，需要在第 1 步当中部署到服务器并启动服务器，然后需要将Android中的代码服务器地址进行修改)  
> 3.可以在<a href="http://download.csdn.net/download/itrenj/9797816"> CSDN中 </a>下载包含服务器的代码的压缩包(压缩包同时也包含了以上MyOkHttp的代码)  
> 4.在<a href="http://blog.csdn.net/itrenj/article/details/69787931"> CSDN博客中 </a>有okhttp简单使用的说明  
