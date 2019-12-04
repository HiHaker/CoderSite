### 软件工程大作业-CoderSite-后端代码
#### 技术: SpringBoot, Swagger-ui, MongoDB, ElasticSearch
#### 数据库设计
```
user:
{
    "userId": {
         type: Object ID,
         unique: true,
         require: true
     },
    "nickname": String,
    "password":  {
         type: String,
         require: true
     },
    "birthday": Date,
    "sex": Boolean,
    "registerDate": Date,
    "avartarId": String,
    "signature":String,
    "mailbox": String,
    "lastLoginTime": Date,
    "labels": [""],
    "coverPicture": String,
    "follows": [
        "userId": String
    ]
}

question:
{
    "qId" : {
        type: Object ID,
        unique: true,
        require: true
     },
    "userId" : {
         type: Object ID,
         require: true
     },
    "postTime" : {
         type: Date,
         require: true
     },
    "label" : [
        "labelName" : String
    ],
    "title" : String,
    "content" : String,
    "likes":[{
        "userId" : Object ID,
        "time" : Date
    }],
    "favorites":[{
        "userId" : Object ID,
        "time" :  Date
    }],
    "answers":[{
        "userId" : Object ID,
        "content" : String,
        "time" :  Date
    }],
    "images" : [
        "imgId" : String
    ]
}

article:
{
    "pId" :  {
         type: Object ID,
         unique: true,
         require: true
     },
    "userId" :  {
         type: Object ID,
         require: true
     },
    "postTime" : {
         type: Date,
         require: true
     },
    "label" : [
        "labelName" : String
    ],
    "title" : String,
    "content" : [
        {
            para: String,
            image: String
        }
    ],
    "likes":[{
        "userId" : Object ID,
        "time" : Date
    }],
    "favorites":[{
        "userId" : Object ID,
        "time" :  Date
    }],
    "comments":[{
        "userId" : Object ID,
        "content" : String,
        "time" :  Date
    }],
    "image" : [
        "imgId" : String
    ]
}

adminUser:
{
    "adminId" :  {
         type: Object ID,
         unique: true,
         require: true
     },    
    "name" : String,
    "password" : {
         type: String,
         unique: true,
         require: true
     },
    "email" : String,
    "lastLoginTime" : Date,
    "adminRole" : String
}

chatList: {
  uId: {
    type: String,
    require: true
  }
  objId: {
    type: String,
    require: true
  }
  message: {
    type: String,
    require: true
  }
  time: {
    type: Date,
    require: true
  }
}
```
对于User来说，昵称、个性签名、标签这些文本信息属性存储在ES数据库。

