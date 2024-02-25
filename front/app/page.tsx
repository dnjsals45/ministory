import Container from '@/components/Container';
import Image from 'next/image';
import metadata from '@/data/metadata';
// import RecentPosts from '@/components/RecentPosts';
// import { allPosts } from 'contentlayer/generated';
// import { InferGetStaticPropsType } from 'next';

const Home = () => {
  return (
    <Container>
      <div className={'my-5 w-full'}>
        <div className={'relative'}>
          <Image
            src={'/logo.jpg'}
            alt="대표 이미지"
            width={100}
            height={100}
            layout={'responsive'}
            objectFit="cover"
            className={'rounded-3xl'}
          />
          <span
            className={`absolute top-12 font-extrabold italic text-white text-8xl md:text-7xl text flex justify-between w-full drop-shadow-lg`}
          >
            {metadata.title}
          </span>
        </div>
        {/*<RecentPosts />*/}
      </div>
    </Container>
  );
};

// export const getStaticProps = async () => {
//   const posts = allPosts.sort((a, b) => Number(new Date(b.date)) - Number(new Date(a.date)));
//   return {
//     props: {
//       posts,
//     },
//   };
// };

export default Home;
