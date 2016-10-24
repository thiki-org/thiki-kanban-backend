
# 零、入口 #

## 初次访问系统时入口 ##

**用例名称**
enter_shouldReturnEntranceSuccessfully

**URL**
http://localhost:8007/entrance

**响应体**
```
{
	"_links":{
		"self":{
			"methods":{
				"modify":{
					"isAllowed":false
				},
				"read":{
					"isAllowed":true
				},
				"create":{
					"isAllowed":false
				},
				"delete":{
					"isAllowed":false
				}
			},
			"href":"http://localhost:8007/entrance"
		},
		"publicKey":{
			"methods":{
				"modify":{
					"isAllowed":true
				},
				"read":{
					"isAllowed":true
				},
				"create":{
					"isAllowed":true
				},
				"delete":{
					"isAllowed":true
				}
			},
			"href":"http://localhost:8007/publicKey"
		},
		"passwordRetrievalApplication":{
			"methods":{
				"modify":{
					"isAllowed":true
				},
				"read":{
					"isAllowed":true
				},
				"create":{
					"isAllowed":true
				},
				"delete":{
					"isAllowed":true
				}
			},
			"href":"http://localhost:8007/passwordRetrievalApplication"
		}
	},
	"description":"Welcome!"
}
```


-------