(ns screenshot-upload.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [backend.utils :as utils]))

(defroutes app-routes
  (GET "/" [] (utils/list-files))
  (POST "/" [:as req] (utils/file-upload req))
  (GET "/:key{[a-zA-Z0-9]{4,8}}" [key] (utils/access-file key)))
  ;; (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-params)
      (wrap-multipart-params)))

(defonce ^:private backend-server (atom nil))

(defn stop-servers [server]
  (@server :timeout 5)
  (reset! server nil))

(defn -main []
  (reset! backend-server (run-server #'app {:port 3000})))
