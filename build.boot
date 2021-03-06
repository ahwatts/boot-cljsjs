(set-env!
  :resource-paths #{"src"}
  :dependencies '[[org.apache.commons/commons-compress "1.14" :scope "test"]
                  [clj-http "3.7.0" :scope "test"]
                  [org.clojure/clojurescript "1.9.946" :scope "test"]
                  [cljsjs/react-dom "16.1.0-0" :scope "test"]
                  [javax.xml.bind/jaxb-api "2.3.1"]
                  [com.sun.xml.bind/jaxb-core "2.3.0.1"]
                  [com.sun.xml.bind/jaxb-impl "2.3.2"]
                  ;; Conflicts with cljs
                  #_[asset-minifier "0.2.6" :scope "test" :exclusions []]])

(def +version+ "0.10.3")

(task-options!
  pom  {:project     'cljsjs/boot-cljsjs
        :version     +version+
        :description "Tooling to package and deploy Javascript libraries for Clojurescript projects"
        :url         "https://github.com/cljsjs/boot-cljsjs"
        :scm         {:url "https://github.com/cljsjs/boot-cljsjs"}
        :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build []
  (comp
   (pom)
   (jar)
   (install)))

(deftask dev []
  (comp
    (watch)
    (build)
    (repl :server true)))

(deftask deploy []
  (comp
   (build)
   (push :repo "clojars" :gpg-sign (not (.endsWith +version+ "-SNAPSHOT")))))
