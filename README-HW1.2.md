# Домашнее задание к занятию «1.2. Формы и форматы передачи данных»

В качестве решения пришлите ссылки на ваши GitHub-проекты в личном кабинете студента на сайте [netology.ru](https://netology.ru).

**Важная информация**

1. Перед стартом работы изучите, пожалуйста, ссылки на главной странице [репозитория с домашними заданиями](../README.md).
2. Если у вас что-то не получилось, тогда оформляйте Issue [по установленным правилам](../report-requirements.md).

## Как сдавать задачи

1. Создайте на вашем компьютере Maven-проект.
1. Инициализируйте в нём пустой Git-репозиторий.
1. Добавьте в него готовый файл [.gitignore](../.gitignore).
1. Добавьте в этот же каталог остальные необходимые файлы.
1. Сделайте необходимые коммиты.
1. Создайте публичный репозиторий на GitHub и свяжите свой локальный репозиторий с удалённым.
1. Сделайте пуш: удостоверьтесь, что ваш код появился на GitHub.
1. Ссылку на ваш проект отправьте в личном кабинете на сайте [netology.ru](https://netology.ru).

## Query

### Легенда

В рамках изучения Java Core и работы протокола HTTP вы использовали библиотеку HttpClient из состава [Apache HttpComponents](https://hc.apache.org).

В состав этой библиотеки входит утилитный класс [URLEncodedUtils](http://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/org/apache/http/client/utils/URLEncodedUtils.html), который и позволяет «парсить» Query String, извлекая параметры.

Необходимо добавить в ваш сервер из предыдущего ДЗ функциональность обработки параметров запроса так, чтобы можно было из объекта типа `Request` отдельно получать и путь запроса, и параметры из Query String.

Например, это можно сделать в виде метода `getQueryParam(String name)` и `getQueryParams()`. Подумайте, что они должны возвращать, исходя из документации на утилитный класс.

### Задача

1. Подключите к своему проекту HttpClient.
1. Реализуйте функциональность по обработке параметров из Query.
1. При необходимости доработайте функциональность поиска хендлера так, чтобы учитывался только путь без Query, т. е. хендлер, зарегистрированный на "/messages", обрабатывал и запросы "/messages?last=10".

### Результат

Реализуйте новую функциональность в ветке `feature/query` вашего репозитория из ДЗ 1 и откройте Pull Request.

Так как вы главный архитектор и проектировщик этого, уже более функционального решения, то все архитектурные решения принимать вам, поэтому будьте готовы к критике со стороны проверяющих.

В качестве решения пришлите ссылку на ваш Pull Request на GitHub в личном кабинете студента на сайте [netology.ru](https://netology.ru).

После того, как домашнее задание будет принято, сделайте `merge` для Pull Request.

## x-www-form-urlencoded* (задача со звёздочкой)

Это необязательная задача, её выполнение не влияет на получение зачёта.

### Легенда

Необходимо добавить в ваш сервер из предыдущего домашнего задания функциональность обработки тела, оформленного в виде `x-www-form-url-encoded` запроса так, чтобы можно было из объекта типа `Request` отдельно получать параметры, переданные в теле запроса.

Например, это можно сделать в виде метода `getPostParam(String name)` и `getPostParams()`. Подумайте, что они должны возвращать, исходя из того, что в передаваемой вам форме может быть два параметра с одинаковым именем.

### Задача

Реализуйте функциональность по обработке тела запроса, если он представлен в виде `x-www-form-urlencoded`.

### Результат

Реализуйте новую функциональность в ветке `feature/form` вашего репозитория из предыдущего домашнего и откройте Pull Request.

Так как вы главный архитектор и проектировщик этого, уже более функционального решения, то все архитектурные решения принимать вам, поэтому будьте готовы к критике со стороны проверяющих.

В качестве результата пришлите ссылку на ваш Pull Request на GitHub в личном кабинете студента на сайте [netology.ru](https://netology.ru).

После того, как домашнее задание будет принято, сделайте `merge` для Pull Request.

## multipart/form-data* (задача со звёздочкой)

Это необязательная задача, её выполнение не влияет на получение зачёта.

### Легенда

Реализуйте полную функциональность и добавьте поддержку multipart-запросов. Используйте для этого библиотеку [FileUpload](http://commons.apache.org/proper/commons-fileupload/).

Например, это можно сделать в виде метода `getPart(String name)` и `getParts()`. Подумайте, что они должны возвращать, исходя из того, что:

* в передаваемой вам форме может быть два параметра с одинаковым именем;
* каждый `Part` может быть как файлом, так и обычным полем.

Это значит, что нужно уметь отличать обычные поля от файлов.

### Задача

Реализуйте функциональность по обработке тела запроса, если он представлен в виде `multipart/form-data`.

Так как вы главный архитектор и проектировщик этого класса, то все архитектурные решения принимать вам, поэтому будьте готовы к критике со стороны проверяющих.

### Результат

Реализуйте новую функциональность в ветке `feature/multipart` вашего репозитория из предыдущего домашнего задания и откройте Pull Request.

Так как вы главный архитектор и проектировщик этого, уже более функционального решения, то все архитектурные решения принимать вам, поэтому будьте готовы к критике со стороны проверяющих.

В качестве решения пришлите ссылку на ваш Pull Request на GitHub в личном кабинете студента на сайте [netology.ru](https://netology.ru).

После того, как домашнее задание будет принято, сделайте `merge` для Pull Request.