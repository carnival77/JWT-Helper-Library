# shorten-url-service
단축 URL 서비스 레포지토리

## 요구사항
1. [bitly](https://bitly.com/) 같은 단축 URL 서비스를 만들어야 합니다.
2. 단축된 URL의 키(Key)는 8글자로 생성되어야 합니다. '단축된 URL의 키'는 'https://bit.ly/3onGWgK' 에서 경로(Path)에 해당하는 '3onGWgK'를 의미합니다. bitly에서는 7글자의 키를 사용합니다.
3. 키 생성 알고리즘은 자유롭게 구현하시면 됩니다.
4. 단축된 URL로 사용자가 요청하면 원래의 URL로 리다이렉트(Redirect)되어야 합니다.
5. 원래의 URL로 다시 단축 URL을 생성해도 항상 새로운 단축 URL이 생성되어야 합니다. 이때 기존에 생성되었던 단축 URL도 여전히 동작해야 합니다.
6. 단축된 URL -> 원본 URL로 리다이렉트 될 때마다 카운트가 증가되어야하고, 해당 정보를 확인할 수 있는 API가 있어야합니다.
7. 데이터베이스 없이 컬렉션을 활용하여 데이터를 저장해야합니다.
8. 기능이 정상 동작하는 것을 확인할 수 있는 적절한 테스트 코드가 있어야 합니다.
9. (선택) 해당 서비스를 사용할 수 있는 UI 페이지를 구현해주세요.

## 필요 API
**경로, HTTP 메서드, 파라미터 전달은 여러분의 의도에 맞게 적절히 구현해주세요.**

1. 단축 URL 생성 API
2. 단축 URL 리다이렉트 API
3. 단축 URL 정보 조회 API

## 프로젝트 개발 결과

### URL 단축 서비스 구현

경로 : lcoalhost:8080/ui/index.html

![image](https://github.com/user-attachments/assets/9ca29af4-ba97-409c-8acd-c4f55dd8b1ee)

### 설명

* 단축 URL 생성 API
  * 기능 : 원본 URL을 입력받아 단축 URL을 생성합니다.
  * 요청 : 
    * 요청 형식 : POST /shortenUrl
    * 파라미터
      * `originalUrl` : 원본 URL
    * 요청 예시
      ```json
      {
        "originalUrl": "https://www.google.com"
      }
      ```
    * 제약 조건 : 
      * 원본 URL은 필수입니다.
      * 원본 URL은 유효한 URL 형식이어야 합니다.
      * 생성 가능한 단축 URL 자원의 수가 최대 '56의 8제곱'을 넘겨 더 이상 생성 가능한 단축 URL 자원이 없을 경우 생성 불가합니다.
* 응답 : 
  * 응답값 : 단축 URL
  * 응답 예시
    ```json
    {
      "shortenUrl": "http://localhost:8080/3onGWgK"
    }
    ```
  * 응답 상세 :
    * 단축 URL은 8글자로 생성됩니다.
    * 단축 URL은 중복되지 않습니다.
    * 단축 URL은 `http://localhost:8080/{key}` 형식으로 리다이렉트됩니다.
  * 단축 URL 리다이렉트 API
    * 기능 : 단축 URL을 입력받아 원본 URL로 리다이렉트합니다. 이때 해당 URL의 리다이렉트 횟수를 증가시킵니다.
    * 요청 : GET /{shortenUrlKey}
    * 응답 : 
      * 응답값 : 원본 URL
      * 응답 상세 :
        * 단축 URL이 존재하지 않을 경우 404 Not Found를 반환합니다.
        * 단축 URL이 존재할 경우 원본 URL로 리다이렉트합니다.
          * 헤더의 `Location`에 원본 URL을 설정합니다.
          * MOVED_PERMANENTLY(301) 상태 코드를 반환합니다.
  * 단축 URL 정보 조회 API
    * 기능 : 단축 URL을 입력받아 해당 URL의 상세 정보를 조회합니다.
    * 요청 : GET /shortenUrl/{shortenUrlKey}
    * 응답 : 
      * 응답값 : 단축 URL의 상세 정보
      * 응답 예시
        ```json
        {
          "shortenUrl": "http://localhost:8080/3onGWgK",
          "originalUrl": "https://www.google.com",
          "redirectCount": 1
        }
        ```
      * 응답 상세 :
        * 단축 URL이 존재하지 않을 경우 404 Not Found를 반환합니다.
        * 단축 URL이 존재할 경우 해당 URL의 상세 정보를 반환합니다.
          * `shortenUrl` : 단축 URL
          * `originalUrl` : 원본 URL
          * `redirectCount` : 리다이렉트 횟수