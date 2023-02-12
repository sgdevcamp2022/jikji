import "./ItemBox.css";
import "./FontAwesome.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import styled from "styled-components";
import { Link } from "react-router-dom";
import ItemBoxTop from "./components/ItemBox/ItemBoxTop.js";
import ItemBoxMiddle from "./components/ItemBox/ItemBoxMiddle.js";
import ItemBoxBottom from "./components/ItemBox/ItemBoxBottom.js";

const ItemWrap = styled.div`
  padding: 20px 0px 20px 0px;
  width: 470px;
  height: auto;
  color: black;
  background-color: white;
  border-bottom: 1px solid lightgray;
  text-align: left;
  text-decoration: none;
  display: inline-block;
`;

const ItemBox = (props) => {
  // 현재 선택된 아이콘을 관리하는 state

  return (
    <ItemWrap>
      <ItemBoxTop userId={props.item.username} />

      <img src={props.item.imageUrl} class="photo" alt="" />
      <div>
        <ItemBoxMiddle likes={props.item.likes} commentNum={props.commentNum} />
        <ItemBoxBottom commentNum={props.item.price} />
      </div>
    </ItemWrap>
  );
};

export default ItemBox;
