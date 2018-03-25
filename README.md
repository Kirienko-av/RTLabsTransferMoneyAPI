# RTLabsTransferMoneyAPI
* **/api/accounts** - получить список сущствующих аккаунтов
* **/api/account?id=1** -получить аккаунт 1
* **/api/transactions** -получить список заведенных и выполненых транзакций
* **/api/transaction?from_account_id=1&to_account_id=2&value=50** - совершить перевод из аккаута 1 в аккаунт 2 в размере 50 уе
                                                              from_account_id либо to_account_id можно не указывать тогда произойдет                                                                     операция добавления или удаления уе
