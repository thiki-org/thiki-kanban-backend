
# 十二 、用户 #

## 获取头像>用户在获取头像时,如果此前头像已经上传,则获取时则返回此前上传的头像 ##

**用例名称**
loadAvatar

**URL**
http://localhost:8007/users/someone/avatar

**响应体**
```
{
	"_links":{
		"profile":{
			"href":"http://localhost:8007/users/someone/profile"
		},
		"self":{
			"href":"http://localhost:8007/users/someone/avatar"
		}
	}
}
```


-------
## 上传头像>用户上传头像时,如果头像文件大小超过限制,则告知客户端相关错误 ##

**用例名称**
nowAllowedIfAvatarWasTooBig

**URL**
http://localhost:8007/users/someone/avatar

**响应体**
```
{
	"timestamp":1475806523944,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"头像文件已经超过100KB限制。",
	"path":"/users/someone/avatar",
	"code":1004
}
```


-------
## 上传头像>用户上传头像时,如果未传头像文件,则告知客户端相关错误 ##

**用例名称**
nowAllowedIfAvatarWasNull

**URL**
http://localhost:8007/users/someone/avatar

**响应体**
```
{
	"timestamp":1475806524001,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"请上传头像文件。",
	"path":"/users/someone/avatar",
	"code":1003
}
```


-------
## 获取头像>用户在获取头像时,如果此前头像没有上传头像,则返回默认头像 ##

**用例名称**
loadDefaultAvatar

**URL**
http://localhost:8007/users/someone/avatar

**响应体**
```
{
	"_links":{
		"profile":{
			"href":"http://localhost:8007/users/someone/profile"
		},
		"self":{
			"href":"http://localhost:8007/users/someone/avatar"
		}
	}
}
```


-------
## 上传头像>用户成功上传头像 ##

**用例名称**
uploadAvatar

**URL**
http://localhost:8007/users/someone/avatar

**响应体**
```
{
	"_links":{
		"profile":{
			"href":"http://localhost:8007/users/someone/profile"
		},
		"self":{
			"href":"http://localhost:8007/users/someone/avatar"
		}
	}
}
```


-------