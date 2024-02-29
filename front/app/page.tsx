import Container from '@/components/Container';
import Image from 'next/image';
import metadata from '@/data/metadata';

const Home = () => {
  return (
    <Container>
      <div className={'my-5 w-full'}>
        <div className={'relative'}>
          <Image
            src={'/logo.jpg'}
            alt="대표 이미지"
            width={400}
            height={400}
            layout={'responsive'}
            className={'rounded-3xl'}
          />
          <span
            className={`absolute top-12 font-extrabold italic text-white text-8xl md:text-7xl text flex justify-between w-full drop-shadow-lg`}
          >
            {metadata.title}
          </span>
        </div>
      </div>
    </Container>
  );
};

export default Home;
