(ns mybank-web-api.logics.conta)

(defn existe-conta?
  "função responsável por verificar se existe a conta em um mapa de contas"
  [contas id-conta]
  (boolean (id-conta contas)))

(defn atualiza-saldo-conta
  "função responsável por atualizar o saldo de uma conta"
  [contas id-conta valor-deposito f]
  (if (existe-conta? contas id-conta)
    (update-in contas [id-conta :saldo] #(f % valor-deposito))
    (throw (ex-info "Conta não localizada." { :type ::conta-nao-encontrada }))))

(defn get-conta
  "buscar uma conta no mapa de contas"
  [contas id-conta]
  (if (existe-conta? contas id-conta)
    (id-conta contas)
    (throw (ex-info "Conta não localizada." { :type ::conta-nao-encontrada }))))
