import Head from 'next/head';
import Nav from './Nav';
import metadata from '@/data/metadata';
import Image from 'next/image';
import { Button } from '@mui/material';

const Container = (props) => {
  return (
    <div className={'mr-12 ml-12 pl-12 pr-12'}>
      <Head>
        <title>{metadata.title}</title>
      </Head>
      <header>
        <div
          className={
            'sticky top-5 bg-white border-b border-black w-full flex flex-row justify-between'
          }
        >
          <Button href={'/'}>
            <Image
              src={'/logo.jpg'}
              alt="로고"
              width={40}
              height={40}
              objectFit={'cover'}
              className={'rounded-full'}
            />
          </Button>
          <Nav />
        </div>
      </header>
      <main>{props.children}</main>
    </div>
  );
};

export default Container;
