import java.util.*;
public class wave {
	public static void main(String[] args) {
		int length = 10;
		int width = 15;
		int[][] field = create_field(length, width);
		int[] start_coord = {6, 2};
		int[] finish_coord = {5, 9};
		fill_field(field);
		bordering_field(field);
		build_obstacle(field);
		set_start_point(field, start_coord);
        System.out.println("Пустое поле.");
		print_field(field);	
		System.out.println("Трассированное поле.");
        int curr_val = 0;
		while(field[finish_coord[0]][finish_coord[1]] == -1) {
            for(int i = 1; i < field.length - 1; i++) {
                for(int j = 1; j < field[i].length - 1; j++) {
                    if(field[i][j] == curr_val) {
                        make_wave(i, j, curr_val, field);
                    }
                }
            }
            curr_val++;
        }
		print_field(field);
        System.out.println();
        
        Stack<Integer> x_routes = new Stack<>();
        x_routes.push(finish_coord[0]);
        Stack<Integer> y_routes = new Stack<>();
        y_routes.push(finish_coord[1]);
        build_route(finish_coord[0], finish_coord[1], field, x_routes, y_routes);
        print_route(x_routes, y_routes);

	}

	static int[][] create_field(int length, int width) {
		int[][] field = new int[length][width];
		return field;
	}

	static void fill_field(int[][] field) {
		for(int i = 0; i < field.length; i++) {
			for(int j = 0; j < field[i].length; j++) {
				field[i][j] = -1;
			}
		}
	}

	static void bordering_field(int[][] field) {
		for(int i = 0; i < field.length; i++) {
			field[i][0] = -50;
			field[i][field[i].length - 1] = -50;
		}
		for(int j = 0; j < field[0].length; j++) {
			field[0][j] = -50;
			field[field.length-1][j] = -50;
		}
	}

	static void build_obstacle(int[][] field) {
		for(int i = 3; i < 8; i++) {
			for(int j = 4; j < 8; j++) {
				field[i][j] = -50;
			}
		}
	}

	static void set_start_point(int[][] field, int[] start_coord) {
		field[start_coord[0]][start_coord[1]] = 0;
	}

	static void print_field(int[][] field) {
		for(int i = 0; i < field.length; i++) {
			for(int j = 0; j < field[i].length; j++) {
				System.out.printf("%3d", field[i][j]);
			}
			System.out.println();
		}
	}

	static void take_above(int x, int y, int val, int[][] field) {
		if(field[x - 1][y] == -1) {
			field[x-1][y] = val + 1;
		}
	}

	static void take_below(int x, int y, int val, int[][] field) {
		if(field[x + 1][y] == -1) {
			field[x+1][y] = val + 1;
		}
	}

	static void take_left(int x, int y, int val, int[][] field) {
		if (field[x][y-1] == -1) {
			field[x][y-1] = val + 1;
		}
	}

	static void take_rigth(int x, int y, int val, int[][] field) {
		if (field[x][y+1] == -1) {
			field[x][y+1] = val + 1;
		}
	}

	static void make_wave(int x, int y, int val, int[][] field) {
		take_above(x, y, val, field);
		take_below(x, y, val, field);
		take_left(x, y, val, field);
		take_rigth(x, y, val, field);
	}

    static void build_route(int x, int y, int[][] field, Stack<Integer> x_routes, Stack<Integer> y_routes) {
        int curr_val = field[x][y];
        if(curr_val != 0) {
            if(field[x-1][y] == curr_val-1) {
                x_routes.push(x-1);
                y_routes.push(y);
                build_route(x-1, y, field, x_routes, y_routes);
            } else {
                if(field[x][y-1] == curr_val-1) {
                    x_routes.push(x);
                    y_routes.push(y-1);
                    build_route(x, y-1, field, x_routes, y_routes);
                } else {
                    if(field[x+1][y] == curr_val-1) {
                        x_routes.push(x+1);
                        y_routes.push(y);
                        build_route(x+1, y, field, x_routes, y_routes);
                    } else {
                        if(field[x][y+1] == curr_val-1) {
                            x_routes.push(x);
                            y_routes.push(y+1);
                            build_route(x, y+1, field, x_routes, y_routes);
                        }
                    }
                }
            }
        }
    }

    static void print_route (Stack<Integer> x_route, Stack<Integer> y_route) {
        System.out.println("Маршрут:");
        Boolean empty_stack = x_route.isEmpty();
        while(empty_stack == false) {
            System.out.printf("Точка (%d, %d)", x_route.pop(), y_route.pop());
            System.out.println();
            empty_stack = x_route.isEmpty();
        }
    }
}
