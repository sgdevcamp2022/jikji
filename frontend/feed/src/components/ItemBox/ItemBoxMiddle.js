import "./ItemBoxMiddle.css";
import "../../FontAwesome.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ItemBoxMiddle = (props) => {
  // 현재 선택된 아이콘을 관리하는 state

  return (
    <div className="itemboxmiddle">
    <FontAwesomeIcon icon="heart" className="itemboxmiddle-icon" />
    <FontAwesomeIcon icon="comment" className="itemboxmiddle-icon" />
    <FontAwesomeIcon icon="paper-plane" className="itemboxmiddle-icon" />
    <FontAwesomeIcon icon="bookmark" className="itemboxmiddle-icon-right" />
      <div>
        <div className="itemboxmiddle-likemessage">
          <span className="itembox-bold">user-example</span>
          <span>님 </span>
          <span className="itembox-bold">외 {props.likes}명</span>
          <span>이 좋아합니다</span>
        </div>
        <span className="itembox-bold">{props.id}</span>
        <span className="itemboxmiddle-text">{props.text}</span>
      </div>
    </div>
  );
};

export default ItemBoxMiddle;
