![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

# Web workers for clojurescript

This [clojurescript](https://clojurescript.org/) library wraps the [web worker api](https://developer.mozilla.org/en-US/docs/Web/API/Web_Workers_API/Using_web_workers) and provides an simple way for multithreading within browsers with cljs.

## Getting started

### Get it / add dependency

Add git dependency in your deps.edn
(https://clojure.org/guides/deps_and_cli#_using_git_libraries)

### Usage

Look at the test example.

#### shadow config for real app

```
̀{:target     :browser
  :modules    {:shared   {:entries []}
              :renderer {:entries    [foo.init]
                          :depends-on #{:shared}}
              :worker   {:entries    [foo.worker.simple]
                          :init-fn    foo.worker.simple/init
                          :depends-on #{:shared}
                          :web-worker true}}
  :devtools   {:browser-inject :renderer
              }}
```                                        

### Notes

* Uses transit for serialize, deserialize
* No Worker Pools
* No Transferables


## Appendix

* standing on the shoulders of giants
* deeply inspired by 
** https://github.com/jtkDvlp/cljs-workers
** https://github.com/federkasten/butler
