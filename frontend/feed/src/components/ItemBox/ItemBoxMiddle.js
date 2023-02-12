import "./ItemBoxMiddle.css";
import "../../FontAwesome.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ItemBoxMiddle = (props) => {
  // 현재 선택된 아이콘을 관리하는 state

  return (
    <div className="itemboxmiddle">
    <FontAwesomeIcon icon="heart" className="icon" />
    <FontAwesomeIcon icon="comment" className="icon" />
    <FontAwesomeIcon icon="paper-plane" className="icon" />
    <FontAwesomeIcon icon="bookmark" className="icon right" />
      <div>
        <span class="item-heart"> you님 외 {props.likes}명이 좋아합니다</span>
      </div>
    </div>
  );
};

export default ItemBoxMiddle;
