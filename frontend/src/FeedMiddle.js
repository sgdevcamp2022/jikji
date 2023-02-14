import axios from "axios";
import { memo, useCallback, useEffect, useState } from "react";
import styled, { createGlobalStyle } from "styled-components";
import ItemBox from "./ItemBox";
import Loader from "./Loader";

const GlobalStyle = createGlobalStyle`
  *, *::before, *::after {
    box-sizing: border-box;
    padding: 0px;
    margin: 0px;
  }

  body {
    background-color: #f2f5f7;
  }
`;

const AppWrap = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  align-items: center;

  .Target-Element {
    width: 100vw;
    height: 140px;
    display: flex;
    justify-content: center;
    text-align: center;
    align-items: center;
  }
`;

const FeedMiddle = () => {
  const [target, setTarget] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [itemLists, setItemLists] = useState([]);
  let index = Number.MAX_SAFE_INTEGER;

  useEffect(() => {
    console.log("itemLists");
    console.log(itemLists);
  }, [itemLists]);

  // api로 받아와서 실행시키는 버전
  // const getMoreItem = async () => {
  //   setIsLoaded(true);
  //   await new Promise((resolve) => setTimeout(resolve, 1500));

  //   const url =
  //     "http://localhost:8080/slicing?id=" + index.toString() + "&size=10";

  //   axios.get(url).then((response) => {
  //     console.log(
  //       "new " + response.data.numberOfElements.toString() + " items"
  //     );
  //     // console.log("index : " + index);
  //     // console.log("hasNext : " + response.data.hasNext.toString());
  //     // console.log("first index : " + response.data.data[0].id);
  //     // console.log("last index : " + response.data.data[response.data.data.length - 1].id);
  //     console.log(response.data.data);
  //     setItemLists((itemLists) => itemLists.concat(response.data.data));
  //     if (response.data.hasNext === true) {
  //       index = response.data.data[response.data.data.length - 1].id;
  //     } else index = -1;
  //   });

  //   setIsLoaded(false);
  // };

  // 임시로 response 객체 생성해서 테스트
  const getMoreItem = async () => {
    setIsLoaded(true);
    await new Promise((resolve) => setTimeout(resolve, 1500));

    // 임시 테스트 위해 객체 생성, 여기서 responseData는 위의 response.data와 같음
    const json = `{
      "numberOfElements": 10,
      "hasNext": true,
      "data": [
        {
          "id": "63ea653d4cfc8024dd542332",
          "contentId": 1,
          "userId": 1,
          "text": "텍스트 본문 내용 #aaa #aab",
          "likes": 1234,
          "imageUrl": [
              {
                  "url": "https://scontent-ssn1-1.xx.fbcdn.net/v/t1.6435-9/182288666_4246933268698221_6094273117017031760_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=9267fe&_nc_ohc=9UPusxWuVPoAX_5Try2&_nc_ht=scontent-ssn1-1.xx&oh=00_AfB-mhrNQa2jci1A2UX9hSUEWWAs-4tBWnQNAdA9isMIRA&oe=64138378",
                  "order": 1,
                  "user_id": 1
              }
          ],
          "hashtags": [
              1,
              2
          ],
          "visibleLikes": null,
          "visibleComments": null,
          "createdAt": "2023-01-01",
          "modifiedAt": "2023-01-01"
      },
      {
        "id": "63ea653d4cfc8024dd542332",
        "contentId": 1,
        "userId": 1,
        "text": "텍스트 본문 내용 #aaa #aab",
        "likes": 1234,
        "imageUrl": [
            {
                "url": "https://scontent-ssn1-1.xx.fbcdn.net/v/t1.6435-9/182288666_4246933268698221_6094273117017031760_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=9267fe&_nc_ohc=9UPusxWuVPoAX_5Try2&_nc_ht=scontent-ssn1-1.xx&oh=00_AfB-mhrNQa2jci1A2UX9hSUEWWAs-4tBWnQNAdA9isMIRA&oe=64138378",
                "order": 1,
                "user_id": 1
            }
        ],
        "hashtags": [
            1,
            2
        ],
        "visibleLikes": null,
        "visibleComments": null,
        "createdAt": "2023-01-01",
        "modifiedAt": "2023-01-01"
    },
    {
      "id": "63ea653d4cfc8024dd542332",
      "contentId": 1,
      "userId": 1,
      "text": "텍스트 본문 내용 #aaa #aab",
      "likes": 1234,
      "imageUrl": [
          {
              "url": "https://scontent-ssn1-1.xx.fbcdn.net/v/t1.6435-9/182288666_4246933268698221_6094273117017031760_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=9267fe&_nc_ohc=9UPusxWuVPoAX_5Try2&_nc_ht=scontent-ssn1-1.xx&oh=00_AfB-mhrNQa2jci1A2UX9hSUEWWAs-4tBWnQNAdA9isMIRA&oe=64138378",
              "order": 1,
              "user_id": 1
          }
      ],
      "hashtags": [
          1,
          2
      ],
      "visibleLikes": null,
      "visibleComments": null,
      "createdAt": "2023-01-01",
      "modifiedAt": "2023-01-01"
  },
  {
    "id": "63ea653d4cfc8024dd542332",
    "contentId": 1,
    "userId": 1,
    "text": "텍스트 본문 내용 #aaa #aab",
    "likes": 1234,
    "imageUrl": [
        {
            "url": "https://scontent-ssn1-1.xx.fbcdn.net/v/t1.6435-9/182288666_4246933268698221_6094273117017031760_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=9267fe&_nc_ohc=9UPusxWuVPoAX_5Try2&_nc_ht=scontent-ssn1-1.xx&oh=00_AfB-mhrNQa2jci1A2UX9hSUEWWAs-4tBWnQNAdA9isMIRA&oe=64138378",
            "order": 1,
            "user_id": 1
        }
    ],
    "hashtags": [
        1,
        2
    ],
    "visibleLikes": null,
    "visibleComments": null,
    "createdAt": "2023-01-01",
    "modifiedAt": "2023-01-01"
},
{
  "id": "63ea653d4cfc8024dd542332",
  "contentId": 1,
  "userId": 1,
  "text": "텍스트 본문 내용 #aaa #aab",
  "likes": 1234,
  "imageUrl": [
      {
          "url": "https://scontent-ssn1-1.xx.fbcdn.net/v/t1.6435-9/182288666_4246933268698221_6094273117017031760_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=9267fe&_nc_ohc=9UPusxWuVPoAX_5Try2&_nc_ht=scontent-ssn1-1.xx&oh=00_AfB-mhrNQa2jci1A2UX9hSUEWWAs-4tBWnQNAdA9isMIRA&oe=64138378",
          "order": 1,
          "user_id": 1
      }
  ],
  "hashtags": [
      1,
      2
  ],
  "visibleLikes": null,
  "visibleComments": null,
  "createdAt": "2023-01-01",
  "modifiedAt": "2023-01-01"
},
{
  "id": "63ea653d4cfc8024dd542332",
  "contentId": 1,
  "userId": 1,
  "text": "텍스트 본문 내용 #aaa #aab",
  "likes": 1234,
  "imageUrl": [
      {
          "url": "https://scontent-ssn1-1.xx.fbcdn.net/v/t1.6435-9/182288666_4246933268698221_6094273117017031760_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=9267fe&_nc_ohc=9UPusxWuVPoAX_5Try2&_nc_ht=scontent-ssn1-1.xx&oh=00_AfB-mhrNQa2jci1A2UX9hSUEWWAs-4tBWnQNAdA9isMIRA&oe=64138378",
          "order": 1,
          "user_id": 1
      }
  ],
  "hashtags": [
      1,
      2
  ],
  "visibleLikes": null,
  "visibleComments": null,
  "createdAt": "2023-01-01",
  "modifiedAt": "2023-01-01"
},
{
  "id": "63ea653d4cfc8024dd542332",
  "contentId": 1,
  "userId": 1,
  "text": "텍스트 본문 내용 #aaa #aab",
  "likes": 1234,
  "imageUrl": [
      {
          "url": "https://scontent-ssn1-1.xx.fbcdn.net/v/t1.6435-9/182288666_4246933268698221_6094273117017031760_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=9267fe&_nc_ohc=9UPusxWuVPoAX_5Try2&_nc_ht=scontent-ssn1-1.xx&oh=00_AfB-mhrNQa2jci1A2UX9hSUEWWAs-4tBWnQNAdA9isMIRA&oe=64138378",
          "order": 1,
          "user_id": 1
      }
  ],
  "hashtags": [
      1,
      2
  ],
  "visibleLikes": null,
  "visibleComments": null,
  "createdAt": "2023-01-01",
  "modifiedAt": "2023-01-01"
},
{
  "id": "63ea653d4cfc8024dd542332",
  "contentId": 1,
  "userId": 1,
  "text": "텍스트 본문 내용 #aaa #aab",
  "likes": 1234,
  "imageUrl": [
      {
          "url": "https://scontent-ssn1-1.xx.fbcdn.net/v/t1.6435-9/182288666_4246933268698221_6094273117017031760_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=9267fe&_nc_ohc=9UPusxWuVPoAX_5Try2&_nc_ht=scontent-ssn1-1.xx&oh=00_AfB-mhrNQa2jci1A2UX9hSUEWWAs-4tBWnQNAdA9isMIRA&oe=64138378",
          "order": 1,
          "user_id": 1
      }
  ],
  "hashtags": [
      1,
      2
  ],
  "visibleLikes": null,
  "visibleComments": null,
  "createdAt": "2023-01-01",
  "modifiedAt": "2023-01-01"
},
{
  "id": "63ea653d4cfc8024dd542332",
  "contentId": 1,
  "userId": 1,
  "text": "텍스트 본문 내용 #aaa #aab",
  "likes": 1234,
  "imageUrl": [
      {
          "url": "https://scontent-ssn1-1.xx.fbcdn.net/v/t1.6435-9/182288666_4246933268698221_6094273117017031760_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=9267fe&_nc_ohc=9UPusxWuVPoAX_5Try2&_nc_ht=scontent-ssn1-1.xx&oh=00_AfB-mhrNQa2jci1A2UX9hSUEWWAs-4tBWnQNAdA9isMIRA&oe=64138378",
          "order": 1,
          "user_id": 1
      }
  ],
  "hashtags": [
      1,
      2
  ],
  "visibleLikes": null,
  "visibleComments": null,
  "createdAt": "2023-01-01",
  "modifiedAt": "2023-01-01"
},
{
  "id": "63ea653d4cfc8024dd542332",
  "contentId": 1,
  "userId": 1,
  "text": "텍스트 본문 내용 #aaa #aab",
  "likes": 1234,
  "imageUrl": [
      {
          "url": "https://scontent-ssn1-1.xx.fbcdn.net/v/t1.6435-9/182288666_4246933268698221_6094273117017031760_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=9267fe&_nc_ohc=9UPusxWuVPoAX_5Try2&_nc_ht=scontent-ssn1-1.xx&oh=00_AfB-mhrNQa2jci1A2UX9hSUEWWAs-4tBWnQNAdA9isMIRA&oe=64138378",
          "order": 1,
          "user_id": 1
      }
  ],
  "hashtags": [
      1,
      2
  ],
  "visibleLikes": null,
  "visibleComments": null,
  "createdAt": "2023-01-01",
  "modifiedAt": "2023-01-01"
}
      ]
  }`;
    const responseData = JSON.parse(json);
    console.log(responseData);

    console.log("new " + responseData.numberOfElements.toString() + " items");
    // console.log("index : " + index);
    // console.log("hasNext : " + response.data.hasNext.toString());
    // console.log("first index : " + response.data.data[0].id);
    // console.log("last index : " + response.data.data[response.data.data.length - 1].id);
    console.log(responseData.data);
    setItemLists((itemLists) => itemLists.concat(responseData.data));
    if (responseData.hasNext === true) {
      index = responseData.data[responseData.data.length - 1].id;
    } else index = -1;

    setIsLoaded(false);
  };

  const onIntersect = async ([entry], observer) => {
    if (entry.isIntersecting && !isLoaded && index >= 0) {
      observer.unobserve(entry.target);
      await getMoreItem(index);
      observer.observe(entry.target);
    }
  };

  useEffect(() => {
    let observer;
    if (target) {
      observer = new IntersectionObserver(onIntersect, {
        threshold: 0.4,
      });
      observer.observe(target);
    }
    return () => observer && observer.disconnect();
  }, [target]);

  return (
    <>
      <GlobalStyle />
      <AppWrap>
        {itemLists.map((v, i) => {
          return <ItemBox item={itemLists[i]} key={i} />;
        })}
        <div ref={setTarget} className="Target-Element">
          {isLoaded && <Loader />}
        </div>
      </AppWrap>
    </>
  );
};

export default memo(FeedMiddle);
