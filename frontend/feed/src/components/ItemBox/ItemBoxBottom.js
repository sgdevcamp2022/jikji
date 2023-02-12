import "./ItemBoxBottom.css";
import "../../FontAwesome.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ItemBoxBottom = (props) => {
  // 현재 선택된 아이콘을 관리하는 state

  return (
    <div className="itemboxbottom">
      <span>댓글 {props.commentNum}개 보기</span>
    </div>
  );
};

export default ItemBoxBottom;
