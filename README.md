# Тестовое задание Senla-GeoCoder
<details>
 <summary>Условие задания</summary>
  
Необходимо разработать приложение для конвертации координат в адрес и из адреса в координаты. Нужно использовать сторонние API (Google, Яндекс, другое). По сути приложение будет прокси с кешированием. Кеширование заключается в том, что если мы отправляли такой же запрос раньше, то запрос к стороннему API отправлен не будет.

Как это работает:

По запросу «Москва, ул. Льва Толстого, 16» геокодер выдаст координаты этого дома — [37.587874, 55.73367]. А если в запросе указать географические координаты нужной точки — скажем, [27.525773, 53.89079], то геокодер вернёт адрес: Минск, Проспект Дзержинского, 5. Если мы снова запросим «Москва, ул. Льва Толстого, 16», то запроса к стороннему API отправлен не будет. Взаимодействие пользователя с приложением происходит через REST API(предпочтительно).

Требования:

- приложение должно быть реализовано на языке Java версии 8+ ;
- код должен соответствовать принципам Low Coupling и принципам ООП;
- код должен соответствовать Java code style (именование переменных, структура класса и др.);
- приложение должно содержать качественную обработку ошибок;
- использовать Spring Framework(Spring Boot).
- покрытие unit тестами(опционально, но будет плюсом).
</details>

## Используемое API
Для геокодирования приложение использует [Яндекс API Геокодер](https://yandex.ru/dev/maps/geocoder/doc/desc/concepts/about.html).

<p>
<details>
<summary>Пример ответа Yandex геокодера в формате json.</summary>


<pre><code>{
    "response": {
        "GeoObjectCollection": {
            "metaDataProperty": {
                "GeocoderResponseMetaData": {
                    "request": "Minsk, Nezavisimosti 54",
                    "results": "10",
                    "found": "1"
                }
            },
            "featureMember": [
                {
                    "GeoObject": {
                        "metaDataProperty": {
                            "GeocoderMetaData": {
                                "precision": "exact",
                                "text": "Беларусь, Минск, проспект Независимости, 54",
                                "kind": "house",
                                "Address": {
                                    "country_code": "BY",
                                    "formatted": "Беларусь, Минск, проспект Независимости, 54",
                                    "Components": [
                                        {
                                            "kind": "country",
                                            "name": "Беларусь"
                                        },
                                        {
                                            "kind": "province",
                                            "name": "Минск"
                                        },
                                        {
                                            "kind": "locality",
                                            "name": "Минск"
                                        },
                                        {
                                            "kind": "street",
                                            "name": "проспект Независимости"
                                        },
                                        {
                                            "kind": "house",
                                            "name": "54"
                                        }
                                    ]
                                },
                                "AddressDetails": {
                                    "Country": {
                                        "AddressLine": "Беларусь, Минск, проспект Независимости, 54",
                                        "CountryNameCode": "BY",
                                        "CountryName": "Беларусь",
                                        "AdministrativeArea": {
                                            "AdministrativeAreaName": "Минск",
                                            "Locality": {
                                                "LocalityName": "Минск",
                                                "Thoroughfare": {
                                                    "ThoroughfareName": "проспект Независимости",
                                                    "Premise": {
                                                        "PremiseNumber": "54"
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        "name": "проспект Независимости, 54",
                        "description": "Минск, Беларусь",
                        "boundedBy": {
                            "Envelope": {
                                "lowerCorner": "27.581756 53.913889",
                                "upperCorner": "27.589966 53.918736"
                            }
                        },
                        "Point": {
                            "pos": "27.585861 53.916313"
                        }
                    }
                }
            ]
        }
    }
}</code></pre>

</details>
</p>

## Использование приложения

Приложения развернуто на платформе Heroku.

<b>Api endpoint для геокодера:</b>
https://yandexproxygeocoder.herokuapp.com/api/geocode/

**Важное замечание: Геокодер принимает и возвращает координаты в порядке: Широта - Долгота.**

### Пример использования:
<pre>
<b>1. Пример прямого геокодирования:</b>
<a href="https://yandexproxygeocoder.herokuapp.com/api/geocode/Сурганова 7а">https://yandexproxygeocoder.herokuapp.com/api/geocode/Сурганова 7а</a>
<b>Ответ:</b> 53.918206 27.602094

<b>2. Пример обратного геокодирования:</b>
<a href="https://yandexproxygeocoder.herokuapp.com/api/geocode/53.89079,%2027.525773">https://yandexproxygeocoder.herokuapp.com/api/geocode/53.89079, 27.525773</a>
<b>Ответ:</b> Беларусь, Минск, проспект Дзержинского, 5
 </pre>


