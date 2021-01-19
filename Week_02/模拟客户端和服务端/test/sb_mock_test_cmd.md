
### sb测验工具模拟测试

#### sb 使用命令简介

```
# 并发请求数
  -c, --concurrency            (Default: 1) Number of concurrent requests
# 请求总数
  -n, --numberOfRequests       (Default: 100) Total number of requests
# 运行测试的秒数。如果指定，-n将被忽略
  -N, --numberOfSeconds        Number of seconds to run the test. If specified, -n will be ignored.
# 延迟毫秒
  -y, --delayInMillisecond     (Default: 0) Delay in millisecond
# 目标url（必填项）
  -u, --url                    Required. Target URL to call. Can include placeholders.
# 请求方式
  -m, --method                 (Default: GET) HTTP Method to use
# 请求模板使用的路径
  -t, --template               Path to request template to use
# 运行测试的秒数。如果指定，-n将被忽略
  -p, --plugin                 Name of the plugin (DLL) to replace placeholders. Should contain one class which implements IValueProvider. Must reside in the same
                               folder.
# 记录运行统计信息的日志文件的路径
  -l, --logfile                Path to the log file storing run stats
# CSV文件的路径，为测试提供替换值
  -f, --file                   Path to CSV file providing replacement values for the test
# 如果您提供带有-f选项而不是CSV的制表符分隔文件（TSV）
  -a, --TSV                    If you provide a tab-separated-file (TSV) with -f option instead of CSV
# 运行单个空运行请求以确保一切正常
  -d, --dryRun                 Runs a single dry run request to make sure all is good
# 在数据中指定日期时间字段。如果设置，将根据记录的顺序和时间发送请求。
  -e, --timedField             Designates a datetime field in data. If set, requests will be sent according to order and timing of records.
# 使用的TLS版本。 TLS 1.0，TLS 1.1和TLS 1.2和SSL3分别接受的值为0、1、2和3
  -g, --TlsVersion             Version of TLS used. Accepted values are 0, 1, 2 and 3 for TLS 1.0, TLS 1.1 and TLS 1.2 and SSL3, respectively
# 提供详细的跟踪信息
  -v, --verbose                provides verbose tracing information
# 令牌
  -b, --tokeniseBody           Tokenise the body
# cookies
  -k, --cookies                Outputs cookies
# 是否使用默认浏览器代理。对于在Fiddler中查看请求/响应很有用
  -x, --useProxy               Whether to use default browser proxy. Useful for seeing request/response in Fiddler.
# 在空运行（调试）模式下仅显示请求
  -q, --onlyRequest            In a dry-run (debug) mode shows only the request.
# 显示请求和响应的标题
  -h, --headers                Displays headers for request and response.
# 将响应保存在-w参数中，或者如果未在\ response_ <timestamp>中提供，则将响应保存
  -z, --saveResponses          saves responses in -w parameter or if not provided in\response_<timestamp>
# 运行测试的秒数。如果指定，-n将被忽略
  -w, --responsesFolder        folder to save responses in if and only if -z parameter is set
# 运行测试的秒数。如果指定，-n将被忽略
  -?, --help                   Displays this help.
# 运行测试的秒数。如果指定，-n将被忽略
  -C, --dontcap                Don't Cap to 50 characters when Logging parameters
# 运行测试的秒数。如果指定，-n将被忽略
  -R, --responseRegex          Regex to extract from response. If it has groups, it retrieves the last group.
# 运行测试的秒数。如果指定，-n将被忽略
  -j, --jsonCount              Captures number of elements under the path e.g. root/leaf1/leaf2 finds count of leaf2 children - stores in the log as another
                               parameter. If the array is at the root of the JSON, use space: -j ' '
# 运行测试的秒数。如果指定，-n将被忽略
  -W, --warmUpPeriod           (Default: 0) Number of seconds to gradually increase number of concurrent users. Warm-up calls do not affect stats.
# 运行测试的秒数。如果指定，-n将被忽略
  -P, --reportSliceSeconds     (Default: 3) Number of seconds as interval for reporting slices. E.g. if chosen as 5, report charts have 5 second intervals.
# 运行测试的秒数。如果指定，-n将被忽略
  -F, --reportFolder           Name of the folder where report files get stored. By default it is in yyyy-MM-dd_HH-mm-ss.ffffff of the start time.
# 运行测试的秒数。如果指定，-n将被忽略
  -B, --dontBrowseToReports    By default it, sb opens the browser with the report of the running test. If specified, it wil not browse.
# 运行测试的秒数。如果指定，-n将被忽略
  -U, --shuffleData            If specified, shuffles the dataset provided by -f option.
# 帮助信息
  --help                       Display this help screen.
# 显示版本信息
  --version                    Display version information.
```




sb -c10 -b10 -u http://127.0.0.1:8801
sb -c10 -b10 -u http://127.0.0.1:8802
sb -c10 -b10 -u http://127.0.0.1:8803