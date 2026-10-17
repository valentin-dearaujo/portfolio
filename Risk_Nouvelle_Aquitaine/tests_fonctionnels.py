import matplotlib.pyplot as plt
from generateImageJules import createHistoEventForDep, createRegression, generateParallelCoord, generateStreamGraphRisques


def test_regression():
    print("Starting test: Regression...")
    try:
        createRegression('température-minimale')
        plt.savefig("test_regression_temperature.png", bbox_inches='tight')
        plt.close()
        print("Success: test_regression_temperature.png")
    except Exception as e:
        print(f"Error during regression: {e}")

def test_parallel_coordinates():
    print("Starting test: Parallel Coordinates...")
    try:
        generateParallelCoord('07-2000')
        plt.savefig("test_parallel_coordinates.png", bbox_inches='tight')
        plt.close()
        print("Success: test_parallel_coordinates.png")
    except Exception as e:
        print(f"Error during parallel coordinates: {e}")

def test_stream_graph_risks():
    print("Starting test: Risks Stream Graph...")
    try:
        generateStreamGraphRisques('Gironde')
        plt.savefig("test_streamgraph_risks.png", bbox_inches='tight')
        plt.close()
        print("Success: test_streamgraph_risks.png")
    except Exception as e:
        print(f"Error during stream graph: {e}")

def run_all_tests():
    print("Starting functional test suite\n" + "-"*50)
    test_regression()
    test_parallel_coordinates()
    test_stream_graph_risks()
    print( "\nEnd of tests.")

if __name__ == "__main__":
    run_all_tests()