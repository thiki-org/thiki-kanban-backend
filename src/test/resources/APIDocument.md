
# 零、入口 #

## 初次访问系统时入口 ##

**用例名称**
enter_shouldReturnEntranceSuccessfully

**URL_TEMPLATE**
http://localhost:9120/kanban/entrance

**响应体**
```
{
	"_links":{
		"self":{
			"href":"http://localhost:9120/kanban/entrance",
			"actions":{
				"read":{
					"isAllowed":true
				}
			}
		},
		"publicKey":{
			"href":"http://localhost:9120/kanban/publicKey",
			"actions":{
				"read":{
					"isAllowed":true
				}
			}
		},
		"passwordRetrievalApplication":{
			"href":"http://localhost:9120/kanban/passwordRetrievalApplication",
			"actions":{
				"read":{
					"isAllowed":true
				}
			}
		}
	},
	"description":"Welcome!"
}
```


-------