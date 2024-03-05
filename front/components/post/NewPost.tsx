// NewPost.tsx 파일의 내용

import dynamic from 'next/dynamic';
import { useState } from 'react';

interface NewPostProps {
  onChangeContent: (newContent: string) => void; // onChangeContent prop 정의
}

const NoSsrWysiwyg = dynamic(() => import('../MyEditor'), { ssr: false });

const NewPost: React.FC<NewPostProps> = ({ onChangeContent }) => {
  const [content, setContent] = useState('');

  const handleContentChange = (newContent: string) => {
    setContent(newContent);
    onChangeContent(newContent); // 내용 변경 시 onChangeContent prop을 호출하여 상위 컴포넌트로 전달
  };

  return (
    <>
      {/* onChangeContent prop을 전달 */}
      <NoSsrWysiwyg onChangeContent={handleContentChange} />
    </>
  );
};

export default NewPost;
