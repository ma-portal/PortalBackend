# API Definition

## 1.user

### get avatar using account

> Authorization is not needed.

#### Endpoint

```GET /user/avatar/:account```

#### Example Response

```
{
    "url": "http://localhost:8080/static/avatar.jpg"
}
```

### add user

#### Endpoint

```POST /user/ Form Data: account={}&password={}```

#### Example Response

```
{
}
```

### sign in

> Authorization is not needed.

#### Endpoint

```POST /user/signin Form Data: account={}&password={}```

#### Example Response

```
{
    "identified": true
}
```

### get profile

#### Endpoint

```GET /user/profile/```

#### Example Response

```
{
    "account": "Luncert",
    "realName": "李经纬",
    "classOf": 2016,
    "joinedTime": {
        "year": 2017,
        "month": 8
    },
    "desc": "主要做Java开发，比较菜，希望大家多多指教！",
    "tags": ["LOL", "Java", "SSM", "React", "Golang"],
    "email": "2725115515@qq.com",
    "qq": "2725115515",
    "phone": "18381196466",
    "avatar": "https://react.semantic-ui.com/images/avatar/large/matthew.png"
}
```

### get projects

#### Endpoint

```GET /user/project/```

#### Example Response

```
[
    {
        "name": "T-W",
        "link": "https://github.com/Luncert/T-W",
        "lastUpdate": "Updated 4 days ago",
        "commit": "38",
        "start": "10",
    },
    {
        "name": "XinAnBackendPoC",
        "link": "https://github.com/Luncert/XinAnBackendPoC",
        "language": {
            "type": "Python",
            "color": "rgb(100, 100, 200)"
        },
        "lastUpdate": "Updated on 8 May",
        "commit": 61,
        "start": 1,
    },
    {
        "name": "flume-iftop-source",
        "link": "https://github.com/Luncert/flume-iftop-source",
        "language": {
            "type": "Java",
            "color": "rgb(200, 160, 100)"
        },
        "lastUpdate": "Updated on 6 May",
        "commit": 11,
        "start": 2,
    },
]
```

## 2.studio

### get top news

#### Endpoint

```GET /studio/top-news```

#### Example Response

```
[
    {
        "title": "互联网+报名要截止啦！",
        "createTime": "",
    }
]
```

### get news

#### Endpoint

```GET /studio/news```

#### Example Response

```
[
    {
        "title": "工作室2019招新开始啦！",
        "createTime": "2019年4月3日",
        "description": "Elliot is a sound engineer living in Nashville who enjoys playing guitar and hanging with his cat."
    },
    {
        "title": "互联网+大赛报名开始啦！",
        "createTime": "2019年4月2日",
        "description": "Elliot is a sound engineer living in Nashville who enjoys playing guitar and hanging with his cat."
    }
]
```

## 3.chat

### get members

#### Endpoint

```GET /chat/member```

#### Example Response

```
[
    {
        "account": "Helen",
        "avatar": "https://react.semantic-ui.com/images/avatar/small/helen.jpg",
        "online": false,
        "unreceivedMsg": 1
    },
    {
        "account": "Christian",
        "avatar": "https://react.semantic-ui.com/images/avatar/small/christian.jpg",
        "online": true,
        "unreceivedMsg": 0
    },
    {
        "account": "Daniel",
        "avatar": "https://react.semantic-ui.com/images/avatar/small/daniel.jpg",
        "online": true,
        "unreceivedMsg": 0
    }
]
```