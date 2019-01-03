### 简介

文件上传

- 上传文件流
- 返回下载地址URL
- 应用服务器
- 不经过Openfire

### 请求地址
```
a.http://#{ip}:#{port}/mobileHttpController/uploadForUrl
b.http://#{ip}:#{port}/mobileHttpController/uploadForJsonUrl
c.http://#{ip}:#{port}/mobileHttpController/uploadForFileId
d.http://#{ip}:#{port}/mobileHttpController/uploadForJsonFileId

```

### 参数

- `user_id{string(64)}`
    - 上传用户`id`
- `mFile{stream}`
    - 文件流
### 返回值
```
a./upload/download?fileId=#{fileId}&fileName=#{fileName};
b.
{
    url:"/upload/download?fileId=#{fileId}&fileName=#{fileName}"
}
c.#{fileId}
d.
{
    fileId:#{fileId}
}

```

- `fileId`
    - 文件`id`

- `fileName`
    - 已经过编码处理`URLEncode.encode("#{fileName}","UTF-8")`

### 图例

![Alt text][demo1]

[demo1]:https://github.com/GepengCn/tlim/blob/master/images/FILE_UPLOAD.png?raw=true
