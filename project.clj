(defproject adventure-system "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [korma "0.4.3"]
                 [org.xerial/sqlite-jdbc "3.20.0"]
                 [cheshire "5.8.0"]
                 [clj-discord "0.1.0-SNAPSHOT"]]
  :main adventure-system.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
