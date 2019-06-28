# 代码生成器
#### 单机版  (spring.profiles.active="standalone")
 1、获取指定数据库表的信息
 `http://xxx:8080/api/generator/allTables`
 参数如下
```json
{
  "jdbcDriverName": "xxx",
  "jdbcDriverUrl": "xxx",
  "jdbcPassword": "xxx",
  "jdbcUserName": "xxx"
}
```
2、选择请求1返回数据中想要生成的表信息，输入作者、输出路径、代码中包名即可生成代码
`http://xxx:8080/api/generator/createCodeStandalone`
 参数如下
```json
{
  "config": {
    "author": "xxx",
    "outPath": "x:/xx/",
    "packageName": "xx.xxx.xx.xxx"
  },
  "dataSourceParams": {
    "jdbcDriverName": "string",
    "jdbcDriverUrl": "string",
    "jdbcPassword": "string",
    "jdbcSchema": "string",
    "jdbcUserName": "string"
  },
  "tables": [
      "xxx" : {"xx": "xx"}
  ]
}
```
3、代码生成在”outPath“中

#### 专业版  (spring.profiles.active="pro")
1、先通过用户模块接口授权获取token
2、获取指定数据库表的信息
 `http://xxx:8080/api/generator/allTables`
 参数如下
```json
{
  "jdbcDriverName": "xxx",
  "jdbcDriverUrl": "xxx",
  "jdbcPassword": "xxx",
  "jdbcUserName": "xxx"
}
```
3、选择请求1返回数据中想要生成的表信息，输入作者、输出路径、代码中包名即可生成代码
`http://xxx:8080/api/generator/createCodePro`
 参数如下
```json
{
  "config": {
    "author": "xxx",
    "outPath": "x:/xx/",
    "packageName": "xx.xxx.xx.xxx"
  },
  "dataSourceParams": {
        "jdbcDriverName": "string",
        "jdbcDriverUrl": "string",
        "jdbcPassword": "string",
        "jdbcSchema": "string",
        "jdbcUserName": "string"
  },
  "tables": [
      "xxx" : {"xx": "xx"}
  ]
}
```
4、代码以压缩包的形式返回