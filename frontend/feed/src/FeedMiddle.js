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

  const getMoreItem = async () => {
    setIsLoaded(true);
    await new Promise((resolve) => setTimeout(resolve, 1500));

    const url =
      "http://localhost:8080/slicing?id=" + index.toString() + "&size=10";

    axios.get(url).then((response) => {
      console.log(
        "new " + response.data.numberOfElements.toString() + " items"
      );
      // console.log("index : " + index);
      // console.log("hasNext : " + response.data.hasNext.toString());
      // console.log("first index : " + response.data.data[0].id);
      // console.log("last index : " + response.data.data[response.data.data.length - 1].id);
      console.log(response.data.data);
      setItemLists((itemLists) => itemLists.concat(response.data.data));
      if (response.data.hasNext === true) {
        index = response.data.data[response.data.data.length - 1].id;
      } else index = -1;
    });

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
