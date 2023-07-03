import Link from "@/types/Link";

interface Item {
    message: string,
    id: number;
    _links: {
        delete: Link;
    }
}

export default Item;