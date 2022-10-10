(ns mybank-web-api.controllers.conta)

(defn get-saldo [request]
  (let [contas (-> request :contas)
        id-conta (-> request :path-params :id keyword)
        conta (id-conta @contas)]
    {:status 200
     :body (id-conta @contas "conta inválida!")}))

(defn make-deposit [request]
  (let [contas (-> request :contas)
        id-conta (-> request :path-params :id keyword)
        conta (id-conta @contas)
        valor-deposito (-> request :body slurp parse-double)]
    (if conta
      (do
        (swap! contas (fn [m] (update-in m [id-conta :saldo] #(+ % valor-deposito))))
        {:status 200
         :body {:id-conta   id-conta
                :novo-saldo (id-conta @contas)}})
      (throw (ex-info "Conta não localiza." { :type ::conta-nao-encontrada })))))
