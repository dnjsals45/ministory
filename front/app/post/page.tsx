import Container from '@/components/Container';

async function fetchData() {
  const data = await fetch('http://localhost:8080/api/v1/contents/1');
  return data.json();
}
const Blog = async () => {
  const response = await fetchData();

  return (
    <Container>
      {response.data.content.complete && <div>{response.data.content.title}</div>}
    </Container>
  );
};
export default Blog;
